package com.example.cellphones.dto.request.order;

import com.example.cellphones.model.OrderStatus;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateOrderStatusReq {

    private Long orderId;
    private OrderStatus status;

}
