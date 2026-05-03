package com.example.computershop.config;

import com.example.computershop.exception.InvalidProductDataException;
import com.example.computershop.exception.InvalidProductTypeException;
import com.example.computershop.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleNotFound(ProductNotFoundException ex) {
        log.warn("Product not found: {}", ex.getMessage());
        return ResponseEntity.status(404).body(
                new ErrorMessageResponse("Product not found", ex.getMessage(), LocalDateTime.now())
        );
    }

    @ExceptionHandler({
            InvalidProductTypeException.class,
            InvalidProductDataException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorMessageResponse> handleBadRequest(RuntimeException ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return ResponseEntity.status(400).body(
                new ErrorMessageResponse("Request error", ex.getMessage(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse> handleValidation(
            MethodArgumentNotValidException ex
    ) {
        log.warn("Validation failed: {}", ex.getMessage());

        String details = ex.getBindingResult().getFieldErrors()
                           .stream()
                           .map(err -> err.getField() + ": " + err.getDefaultMessage())
                           .collect(Collectors.joining("; "));

        ErrorMessageResponse errorResponse = new ErrorMessageResponse(
                "Ошибка валидации",
                details,
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessageResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation: {}", ex.getMessage());

        String detail = "Нарушение уникальности данных. Возможно, товар с таким серийным номером уже существует.";

        ErrorMessageResponse error = new ErrorMessageResponse(
                "Конфликт данных",
                detail,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}
