package com.example.computershop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("LAPTOP")
@Getter
@Setter
@NoArgsConstructor
public class Laptop extends Product {

    private int screenSize;

    public Laptop(
            String serialNumber,
            String manufacturer,
            BigDecimal price,
            int quantity,
            int screenSize
    ) {
        super(serialNumber, manufacturer, price, quantity);
        this.screenSize = screenSize;
    }
}
