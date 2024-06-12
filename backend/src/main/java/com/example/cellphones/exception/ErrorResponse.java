package com.example.cellphones.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String message;

}
