package com.example.cellphones.dto.request.order;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateOrderReq {
    private List<OrderDetailReq> listOrderProduct;
    private String note;
    private String payment;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String giveBackDay;
    private Long totalAmount;
    private  List<Long> listSelectedCartDetailId;
}
