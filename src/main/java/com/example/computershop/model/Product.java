package com.example.computershop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorColumn(
        name = "product_type",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String serialNumber;

    private String manufacturer;
    private BigDecimal price;
    private int quantity;

    public Product(
            String serialNumber,
            String manufacturer,
            BigDecimal price,
            int quantity
    ) {
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
    }

}
