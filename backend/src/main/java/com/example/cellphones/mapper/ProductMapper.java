package com.example.cellphones.mapper;

import com.example.cellphones.dto.ProductDto;
import com.example.cellphones.model.Product;

public class ProductMapper {
    public ProductMapper() {
    }
    public static ProductDto responseProductDtoFromModel(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .describe(product.getDescribe())
                .author(product.getAuthor())
                .price(product.getPrice())
                .image(product.getImage())
                .quantity(product.getQuantity())
                .deposit(product.getDeposit())
                .category(CategoryMapper.responseCategoryDtoFromModel(product.getCategory()))
                .build();
    }
}
