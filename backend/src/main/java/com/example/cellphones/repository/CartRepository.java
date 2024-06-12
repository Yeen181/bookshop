package com.example.cellphones.repository;

import com.example.cellphones.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT cart from Cart cart where :userId is NULL OR cart.user.id =:userId")
    Cart findCartByUserId(Long userId);
}
