package com.example.cellphones.controller;

import com.example.cellphones.dto.request.category.UpdateCategoryReq;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(path = "/")
    public ResponseEntity<?> getCategory() {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(categoryService.getCategories())
                        .build()
        );
    }

    @PostMapping(path = "/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> createCategory(@RequestBody UpdateCategoryReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(categoryService.createCategory(req.getName()))
                        .build()
        );
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody UpdateCategoryReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(categoryService.updateCategory(Long.parseLong(id), req.getName()))
                        .build()
        );
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        boolean deleted = categoryService.deleteCategory(Long.parseLong(id));
        if (deleted) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .data("Deleted category address successfully !")
                            .build());
        }
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data("Deleted category address failed !")
                        .build());
    }
}
