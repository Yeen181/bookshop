package com.example.cellphones.dto;

import com.example.cellphones.model.CartDetail;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartDto {
    private Long id;
    private List<CartDetail> listCartDetail;
}
