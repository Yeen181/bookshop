package com.example.cellphones.dto.request.cart;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateCartDetailReq {
    private Long productId;
    private int quantity;
}
