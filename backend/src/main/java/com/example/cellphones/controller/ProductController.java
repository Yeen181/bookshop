package com.example.cellphones.controller;
import com.example.cellphones.dto.request.product.UpdateProductReq;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping(path = "/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @DeleteMapping(path = "/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(productService.deleteProduct(Long.parseLong(id)))
                        .build());
    }

    @PostMapping(path = "/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> createProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("describe") String describe,
            @RequestParam("author") String author,
            @RequestParam("price") Integer price,
            @RequestParam("deposit") Integer deposit,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("quantity") Integer quantity
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(productService.createProduct(name, describe, author, deposit, price, categoryId, quantity, file))
                        .build()
        );
    }

    @PutMapping(path = "/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody UpdateProductReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(productService.updateProduct(Long.parseLong(id), req))
                        .build()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(productService.getProductById(Long.parseLong(id)))
                        .build()
        );
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchProduct(@Param("searchText") String searchText,
                                           @Param("categoryId") Long categoryId,
                                           @Param("priceFrom") Integer priceFrom,
                                           @Param("priceTo") Integer priceTo,
                                           @Param("page") Integer page,
                                           @Param("limit") Integer limit,
                                           @Param("isPaginate") Boolean isPaginate) {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(productService.searchProduct(searchText, categoryId, priceFrom, priceTo, page, limit, isPaginate))
                .build());
    }
}
