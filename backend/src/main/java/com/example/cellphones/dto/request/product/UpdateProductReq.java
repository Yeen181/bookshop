package com.example.cellphones.dto.request.product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateProductReq {
    private String name;
    private String describe;
    private String author;
    private int price;
    private int deposit;
}
