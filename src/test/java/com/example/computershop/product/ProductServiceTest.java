package com.example.computershop.product;

import com.example.computershop.dto.ProductDto;
import com.example.computershop.dto.ProductFactory;
import com.example.computershop.dto.ProductMapper;
import com.example.computershop.model.DesktopComputer;
import com.example.computershop.repository.ProductRepository;
import com.example.computershop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductFactory productFactory;

    @InjectMocks
    private ProductService productService;

    private DesktopComputer desktopComputer;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productService = new ProductService(
                productRepository,
                productMapper,
                productFactory
        );

        productDto = ProductDto.builder()
                .type("DESKTOP")
                .serialNumber("D-001")
                .manufacturer("Dell")
                .price(new BigDecimal("999.99"))
                .quantity(15)
                .formFactor("DESKTOP")
                .build();

        desktopComputer = new DesktopComputer(
                "D-001",
                "Dell",
                new BigDecimal("999.99"),
                15,
                DesktopComputer.FormFactor.DESKTOP
        );
        desktopComputer.setId(1L);
    }

    @Test
    void shouldReturnCreatedProduct() {
        when(productMapper.toEntity(productDto))
                .thenReturn(desktopComputer);

        when(productRepository.save(desktopComputer))
                .thenReturn(desktopComputer);

        when(productMapper.toDto(desktopComputer))
                .thenReturn(productDto);

        ProductDto result = productService.addProduct(productDto);

        assertThat(result.getType()).isEqualTo("DESKTOP");
        assertThat(result.getSerialNumber()).isEqualTo("D-001");

        verify(productMapper).toEntity(productDto);
        verify(productRepository).save(desktopComputer);
        verify(productMapper).toDto(desktopComputer);
    }

}
