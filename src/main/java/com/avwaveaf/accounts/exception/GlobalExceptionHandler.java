package com.avwaveaf.accounts.exception;

import com.avwaveaf.accounts.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExists.class)
    public ResponseEntity<ErrorResponseDTO> customerAlreadyExists(CustomerAlreadyExists ex, WebRequest request) {
        ErrorResponseDTO err = ErrorResponseDTO.builder()
                .apiPath(request.getDescription(false))
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(ex.getMessage())
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponseDTO err = ErrorResponseDTO.builder()
                .apiPath(request.getDescription(false))
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(ex.getMessage())
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }



}
