package com.example.cellphones.dto.request.order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailReq {
    private Long productId;
    private int quantity;
}
