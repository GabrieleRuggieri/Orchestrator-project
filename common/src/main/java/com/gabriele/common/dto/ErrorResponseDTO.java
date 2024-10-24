package com.gabriele.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
    private String error;
    private String message;
    private Integer statusCode;
}
