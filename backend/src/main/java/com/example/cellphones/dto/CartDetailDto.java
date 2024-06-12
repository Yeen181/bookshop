package com.example.cellphones.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartDetailDto {
    private Long id;
    private ProductDto productDto;
    private int quantity;
}
