package com.example.cellphones.service;

import com.example.cellphones.dto.CategoryDto;
import com.example.cellphones.response.ResponseObject;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(String name);

    boolean deleteCategory(Long id);

    List<CategoryDto> getCategories();

    CategoryDto updateCategory(Long id, String name);
}
