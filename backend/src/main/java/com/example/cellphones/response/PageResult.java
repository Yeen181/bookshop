package com.example.cellphones.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class PageResult<T> {
    private long totalItems;
    private T data;
    private int currentPage;
    private int limit;
    private int totalPages;
}
