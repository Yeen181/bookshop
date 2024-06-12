package com.example.cellphones.dto.request.product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SearchProductReq {
    private String contains;
}
