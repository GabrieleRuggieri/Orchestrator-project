package com.gabriele.exception.handler;

import com.gabriele.common.dto.ErrorResponseDTO;
import com.gabriele.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    //todo da rifinire insieme al dto
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleConversion(ItemNotFoundException ex) {
        ex.printStackTrace();
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(ex.getErrorMessage().getError())
                .message(ex.getErrorMessage().getMessage())
                .statusCode(ex.getStatusCode().value())
                .build();
        return new ResponseEntity<>(errorResponseDTO, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConnversion(RuntimeException ex) {
        log.info(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
