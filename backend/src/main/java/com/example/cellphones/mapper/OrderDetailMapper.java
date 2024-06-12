package com.example.cellphones.mapper;
import com.example.cellphones.dto.OrderDetailDto;
import com.example.cellphones.model.OrderDetail;

public class OrderDetailMapper {
    public static OrderDetailDto responseOrderDetailDtoFromModel(OrderDetail orderDetail){
        return OrderDetailDto.builder()
                .id(orderDetail.getId())
                .productName(orderDetail.getProduct() == null ? "Đơn hàng đã bị xóa" : orderDetail.getProduct().getName())
                .price(orderDetail.getProduct() == null ? 0 : orderDetail.getProduct().getPrice())
                .image(orderDetail.getProduct() == null ? "Đơn hàng đã bị xóa" : orderDetail.getProduct().getImage())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}
