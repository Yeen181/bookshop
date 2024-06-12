package com.example.cellphones.service.impl;

import com.example.cellphones.dto.ProductDto;
import com.example.cellphones.dto.request.product.UpdateProductReq;
import com.example.cellphones.exception.CategoryNotFoundByIdException;
import com.example.cellphones.exception.CustomException;
import com.example.cellphones.exception.ProductNotFoundByIdException;
import com.example.cellphones.mapper.ProductMapper;
import com.example.cellphones.model.Category;
import com.example.cellphones.model.Product;
import com.example.cellphones.repository.CartDetailRepository;
import com.example.cellphones.repository.CategoryRepository;
import com.example.cellphones.repository.ProductRepository;
import com.example.cellphones.response.PageResult;
import com.example.cellphones.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final CartDetailRepository cartDetailRepository;


    @Override
    public ProductDto createProduct(String name, String describe, String author, Integer deposit, Integer price, Long categoryId, Integer quantity, MultipartFile file) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundByIdException(categoryId));
        try {
            Product product = Product.builder()
                    .name(name)
                    .describe(describe)
                    .author(author)
                    .price(price)
                    .isDelete(false)
                    .deposit(deposit)
                    .category(category)
                    .quantity(quantity)
                    .build();
            //convert image base 64
            InputStream inputStream = file.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] data = outputStream.toByteArray();
            String imageBase64 = Base64.getEncoder().encodeToString(data);
            inputStream.close();
            outputStream.close();

            product.setImage(imageBase64);
            product = this.productRepo.save(product);
            return (ProductMapper.responseProductDtoFromModel(product));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductDto updateProduct(Long id, UpdateProductReq request) {
        Product oldProduct = this.productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundByIdException(id));
        oldProduct.setName(request.getName());
        oldProduct.setDescribe(request.getDescribe());
        oldProduct.setAuthor(request.getAuthor());
        oldProduct.setDeposit(request.getDeposit());
        oldProduct.setPrice(request.getPrice());
        oldProduct = this.productRepo.saveAndFlush(oldProduct);
        return (ProductMapper.responseProductDtoFromModel(oldProduct));
    }

    @Override
    public String deleteProduct(Long id) {
        try {
            Product product = this.productRepo.findById(id)
                    .orElseThrow(() -> new ProductNotFoundByIdException(id));
            product.setIsDelete(true);
            this.productRepo.save(product);
//            product.getCategory().getProducts().remove(product);
//            product.getCartDetails().forEach(p -> p.setProduct(null));
//            product.getOrderDetails().forEach(p -> p.setProduct(null));
//            this.cartDetailRepository.deleteAll(product.getCartDetails());
//            this.orderDetailRepository.deleteAll(product.getOrderDetails());
//            this.productRepo.delete(product);
            return "Successfully";
        } catch (Exception e) {
            throw new CustomException("not delete product");
        }
    }

    @Override
    public Object searchProduct(String searchText, Long categoryId, Integer priceFrom, Integer priceTo, Integer page, Integer limit, Boolean isPaginate) {
        if (isPaginate) {
            Pageable pageable = getPageable(page, limit, null, "DESC");
            Page<Product> products = this.productRepo.getPaginate(searchText, categoryId, priceFrom, priceTo, pageable);
            return PageResult.builder()
                    .data(products.stream()
                            .map(ProductMapper::responseProductDtoFromModel)
                            .collect(Collectors.toList()))
                    .currentPage(products.getNumber())
                    .limit(limit)
                    .totalItems(products.getTotalElements())
                    .totalPages(products.getTotalPages())
                    .build();
        }
        return this.productRepo.findAll().stream()
                .map(ProductMapper::responseProductDtoFromModel)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = this.productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundByIdException("Product not found by id ", id));
        return (ProductMapper.responseProductDtoFromModel(product));
    }


    protected Pageable getPageable(Integer page, Integer size, String sortBy, String sortOrder) {
        page = (page == null) ? 0 : page;
        size = (size == null) ? 20 : size;
        sortBy = sortBy == null || sortBy.isEmpty() ? "id" : sortBy;
        Sort.Direction sortOrderRequest = sortOrder == null || sortOrder.isEmpty() ? Sort.Direction.ASC : Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(sortOrderRequest, sortBy);
        return PageRequest
                .of(page, size, sort);
    }
}
