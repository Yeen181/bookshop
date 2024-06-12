package com.example.cellphones.mapper;

import com.example.cellphones.dto.CategoryDto;
import com.example.cellphones.model.Category;

public class CategoryMapper {
    public static CategoryDto responseCategoryDtoFromModel(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
