package com.example.cellphones.mapper;

import com.example.cellphones.dto.OrderDto;
import com.example.cellphones.model.Order;

import java.util.stream.Collectors;

public class OrderMapper {
    public OrderMapper() {
    }

    public static OrderDto responseOrderDtoFromModel(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .listOrderDetail(order.getListOrderDetail().stream()
                        .map(OrderDetailMapper::responseOrderDetailDtoFromModel)
                        .collect(Collectors.toList()))
                .total(order.getTotal())
                .note(order.getNote())
                .payment(order.getPayment())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .receiverAddress(order.getReceiverAddress())
                .timeOrder(order.getTimeOrder())
                .giveBackDay(order.getGiveBackDay())
                .status(order.getStatus())
                .build();
    }
}
