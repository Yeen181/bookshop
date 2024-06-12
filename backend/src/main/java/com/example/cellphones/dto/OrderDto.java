package com.example.cellphones.dto;

import com.example.cellphones.model.OrderStatus;
import lombok.*;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDto {
    private Long id;
    private List<OrderDetailDto> listOrderDetail;
    private long total;
    private String note;
    private String payment;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private Date giveBackDay;
    private Date timeOrder;
    private OrderStatus status;
}
