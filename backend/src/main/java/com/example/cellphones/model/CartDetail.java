package com.example.cellphones.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "tbl_cart_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(referencedColumnName = "id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Product product;

    private int quantity;
}
