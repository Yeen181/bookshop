package com.example.cellphones.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailDto {
    private Long id;
    private String productName;
    private int price;
    private String image;
    private int quantity;
}
