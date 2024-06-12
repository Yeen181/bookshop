package com.example.cellphones.dto.request.cart;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartDetailReq {
    private String name;
    private int quantity;
}
