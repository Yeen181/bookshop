package com.example.cellphones.service.impl;

import com.example.cellphones.dto.OrderDto;
import com.example.cellphones.dto.request.order.CreateOrderReq;
import com.example.cellphones.dto.request.order.OrderDetailReq;
import com.example.cellphones.dto.request.order.UpdateOrderStatusReq;
import com.example.cellphones.exception.ProductNotFoundByIdException;
import com.example.cellphones.exception.UserNotFoundByIdException;
import com.example.cellphones.mapper.OrderMapper;
import com.example.cellphones.model.*;
import com.example.cellphones.model.user.User;
import com.example.cellphones.repository.*;
import com.example.cellphones.response.PageResult;
import com.example.cellphones.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {
    final private OrderRepository orderRepo;

    final private ProductRepository productRepo;

    final private UserRepository userRepo;

    private final CartRepository cartRepo;

    private final CartDetailRepository cartDetailRepo;


    @Override
    public List<OrderDto> getOrderOfUser(Long userId) {
        List<Order> listOrder = this.orderRepo.findByUserId(userId);
        return (listOrder.stream().map(OrderMapper::responseOrderDtoFromModel).collect(Collectors.toList()));
    }

    @Override
    public List<OrderDto> getOrders() {
        List<Order> listOrder = this.orderRepo.findAll();
        return (listOrder.stream().map(OrderMapper::responseOrderDtoFromModel).collect(Collectors.toList()));
    }

    @Override
    public OrderDto createOrder(CreateOrderReq request, Long userId) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date now = new Date();
            int tmpTotal = 0;
            User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundByIdException(userId));
            List<OrderDetailReq> listOrderDetailReq = request.getListOrderProduct();
            List<OrderDetail> listOrderDetail = new ArrayList<>();

            Order order = Order.builder()
                    .user(user)
                    .note(request.getNote())
                    .payment(request.getPayment())
                    .receiverName(request.getReceiverName())
                    .receiverPhone(request.getReceiverPhone())
                    .receiverAddress(request.getReceiverAddress())
                    .timeOrder(now)
                    .giveBackDay(formatter.parse(request.getGiveBackDay()))
                    .status(OrderStatus.NEW)
                    .build();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            // Ngày lúc 0h
            Date dateAtMidnight = calendar.getTime();
            Date dateGiveBack = formatter.parse(request.getGiveBackDay());
            // Tính khoảng cách giữa hai ngày
            long distance = Math.abs(dateGiveBack.getTime() - calendar.getTime().getTime());
            long days = distance / (1000 * 60 * 60 * 24) + 1;

            if (request.getListSelectedCartDetailId().isEmpty()) {
                for (OrderDetailReq orderDetailReq : listOrderDetailReq) {
                    Product product = productRepo.findById(orderDetailReq.getProductId()).orElseThrow(() -> new ProductNotFoundByIdException(orderDetailReq.getProductId()));
                    OrderDetail orderDetail = OrderDetail.builder()
                            .product(product)
                            .quantity(orderDetailReq.getQuantity())
                            .order(order)
                            .build();
                    listOrderDetail.add(orderDetail);
                    tmpTotal += (product.getPrice() * days + product.getDeposit()) * orderDetailReq.getQuantity();
                    product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
                    productRepo.saveAndFlush(product);
                }
                //user.getCart().setCartDetails(null);
                order.setTotal(tmpTotal);
                order.setListOrderDetail(listOrderDetail);
                order = this.orderRepo.save(order);
            } else {
                for (int i = 0; i < request.getListSelectedCartDetailId().size(); i++) {
                    CartDetail cartDetail = cartDetailRepo.findById(request.getListSelectedCartDetailId().get(i)).orElseThrow();
                    Product product = productRepo.findById(cartDetail.getProduct().getId()).orElseThrow(() -> new ProductNotFoundByIdException(cartDetail.getProduct().getId()));
                    OrderDetail orderDetail = OrderDetail.builder()
                            .product(product)
                            .quantity(cartDetail.getQuantity())
                            .order(order)
                            .build();
                    listOrderDetail.add(orderDetail);
                    tmpTotal += (product.getPrice() * days + product.getDeposit()) * cartDetail.getQuantity();
                    product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
                    productRepo.saveAndFlush(product);
                    cartDetailRepo.deleteById(request.getListSelectedCartDetailId().get(i));
                }
                Cart cart = cartRepo.findCartByUserId(user.getId());
                cart.getCartDetails().clear();
//                cart.setCartDetails(new ArrayList<>());
//                cartRepo.saveAndFlush(cart);
                order.setTotal(tmpTotal);
                order.setListOrderDetail(listOrderDetail);
                order = this.orderRepo.save(order);
            }
            return (OrderMapper.responseOrderDtoFromModel(order));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object searchOrder(String contains, Long userId, Integer month, Integer page, Integer limit, Boolean isPaginate) throws ParseException {

        if (isPaginate) {
            Pageable pageable = getPageable(page, limit, null, "DESC");
            Page<Order> orders = this.orderRepo.search(contains, userId, month, pageable);
            return PageResult.builder()
                    .data(orders.stream()
                            .map(OrderMapper::responseOrderDtoFromModel)
                            .collect(Collectors.toList()))
                    .currentPage(orders.getNumber())
                    .limit(limit)
                    .totalItems(orders.getTotalElements())
                    .totalPages(orders.getTotalPages())
                    .build();
        }
        return this.orderRepo.findAll().stream()
                .map(OrderMapper::responseOrderDtoFromModel)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrderStatus(UpdateOrderStatusReq request) {
        Order order = this.orderRepo.findById(request.getOrderId()).orElseThrow();
        order.setStatus(request.getStatus());
        order = this.orderRepo.save(order);
        return (OrderMapper.responseOrderDtoFromModel(order));
    }


    protected Pageable getPageable(Integer page, Integer size, String sortBy, String sortOrder) {
        page = (page == null) ? 0 : page;
        size = (size == null) ? 20 : size;
        sortBy = sortBy == null || sortBy.isEmpty() ? "id" : sortBy;
        Sort.Direction sortOrderRequest = sortOrder == null || sortOrder.isEmpty() ? Sort.Direction.ASC : Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(sortOrderRequest, sortBy);
        return PageRequest
                .of(page, size, sort);
    }
}
