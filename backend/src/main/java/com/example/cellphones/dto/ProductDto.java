package com.example.cellphones.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String describe;
    private String author;
    private int price;
    private String image;
    private int quantity;
    private int deposit;
    private CategoryDto category;
}
