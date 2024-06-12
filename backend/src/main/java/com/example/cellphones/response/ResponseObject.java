package com.example.cellphones.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseObject {
    private HttpStatus status;
    private Object data;
}
