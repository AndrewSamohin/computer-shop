package com.example.computershop.service;

import com.example.computershop.dto.ProductDto;
import com.example.computershop.dto.ProductFactory;
import com.example.computershop.dto.ProductMapper;
import com.example.computershop.exception.ProductNotFoundException;
import com.example.computershop.model.Product;
import com.example.computershop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductFactory productFactory;

    public ProductDto addProduct(ProductDto dto) {
        Product product = productMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product existing = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        existing.setSerialNumber(dto.getSerialNumber());
        existing.setManufacturer(dto.getManufacturer());
        existing.setPrice(dto.getPrice());
        existing.setQuantity(dto.getQuantity());

        productMapper.updateEntityFromDto(existing, dto);

        Product updated = productRepository.save(existing);
        return productMapper.toDto(updated);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByType(String type) {
        Class<? extends Product> productClass = productFactory.resolveClass(type);
        List<Product> products = productRepository.findByProductClass(productClass);
        return products.stream()
                       .map(productMapper::toDto)
                       .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toDto(product);
    }

}
