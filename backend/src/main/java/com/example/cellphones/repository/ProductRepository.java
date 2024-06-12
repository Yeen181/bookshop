package com.example.cellphones.repository;

import com.example.cellphones.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where (:searchText is null or (p.name like %:searchText% or p.author like %:searchText%) " +
            "and (:categoryId is null or :categoryId = p.category.id) " +
            "and (p.isDelete = false)" +
            "and (:priceFrom is null or :priceFrom < p.price) " +
            "and (:priceTo is null or :priceTo > p.price))")
    Page<Product> getPaginate(String searchText, Long categoryId, Integer priceFrom, Integer priceTo, Pageable pageable);
}
