package com.example.computershop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    private Long id;

    @NotBlank(message = "Type cannot be empty")
    private String type;

    @NotBlank(message = "Serial number cannot be empty")
    private String serialNumber;

    @NotBlank(message = "Manufacturer cannot be empty")
    private String manufacturer;

    @Positive(message = "The price must be positive")
    private BigDecimal price;

    @Positive(message = "The quantity must be positive")
    private Integer quantity;

    private String formFactor;
    private Integer screenSize;
    private Double diagonal;
    private Integer capacity;

}
