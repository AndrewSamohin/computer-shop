package com.example.computershop.controller;

import com.example.computershop.dto.ProductDto;
import com.example.computershop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Товары", description = "Управление товарами магазина компьютеров")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(
            summary = "Добавить новый товар",
            description = "Создаёт товар любого типа"
    )
    public ResponseEntity<ProductDto> addProduct(
            @Valid @RequestBody ProductDto dto
    ) {
        ProductDto created = productService.addProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить существующий товар",
            description = "Обновляет все поля товара по его идентификатору"
    )
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto dto
    ) {
        ProductDto updated = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    @Operation(
            summary = "Получить список товаров по типу",
            description = "Возвращает все товары указанного типа"
    )
    public ResponseEntity<List<ProductDto>> getAllByType(
            @RequestParam("type") String type
    ) {
        List<ProductDto> products = productService.getProductsByType(type);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить товар по ID",
            description = "Возвращает один товар по его идентификатору"
    )
    public ResponseEntity<ProductDto> getById(
            @PathVariable Long id
    ) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

}
