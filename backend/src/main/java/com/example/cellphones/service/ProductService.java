package com.example.cellphones.service;



import com.example.cellphones.dto.ProductDto;
import com.example.cellphones.dto.request.product.UpdateProductReq;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductDto createProduct(String name, String describe, String author, Integer deposit, Integer price, Long categoryId, Integer quantity, MultipartFile file);
    ProductDto updateProduct(Long id, UpdateProductReq request);
    String deleteProduct(Long id);
    Object searchProduct(String searchText, Long categoryId, Integer priceFrom, Integer priceTo, Integer page, Integer limit, Boolean isPaginate);
    ProductDto getProductById(Long id);
}
