package com.example.cellphones.service;

import com.example.cellphones.dto.OrderDto;
import com.example.cellphones.dto.request.order.CreateOrderReq;
import com.example.cellphones.dto.request.order.UpdateOrderStatusReq;

import java.text.ParseException;
import java.util.List;

public interface OrderService {
    List<OrderDto> getOrderOfUser(Long userId);

    List<OrderDto> getOrders();

    OrderDto createOrder(CreateOrderReq request, Long userId);

    Object searchOrder(String contains, Long userId, Integer month, Integer page, Integer limit, Boolean isPaginate) throws ParseException;

    OrderDto updateOrderStatus(UpdateOrderStatusReq request);
}
