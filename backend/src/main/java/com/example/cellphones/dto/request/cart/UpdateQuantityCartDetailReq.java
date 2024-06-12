package com.example.cellphones.dto.request.cart;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateQuantityCartDetailReq {
    private Long cartDetailId;
    private int quantity;
}
