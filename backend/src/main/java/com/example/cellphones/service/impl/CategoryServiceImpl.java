package com.example.cellphones.service.impl;

import com.example.cellphones.dto.CategoryDto;
import com.example.cellphones.exception.CategoryNotFoundByIdException;
import com.example.cellphones.mapper.CategoryMapper;
import com.example.cellphones.model.Category;
import com.example.cellphones.repository.CategoryRepository;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.response.ResponseStatus;
import com.example.cellphones.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;
    @Override
    public CategoryDto createCategory(String name) {
        try {
            Category category = Category.builder()
                    .name(name)
                    .build();
            category = this.categoryRepo.save(category);
            return (CategoryMapper.responseCategoryDtoFromModel(category));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCategory(Long id) {
        try {
            this.categoryRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        return (categories.stream().map(CategoryMapper::responseCategoryDtoFromModel).collect(Collectors.toList()));
    }

    @Override
    public CategoryDto updateCategory(Long id, String name) {
        Category oldCategory = this.categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundByIdException(id));
        oldCategory.setName(name);
        oldCategory = this.categoryRepo.saveAndFlush(oldCategory);
        return (CategoryMapper.responseCategoryDtoFromModel(oldCategory));
    }
}
