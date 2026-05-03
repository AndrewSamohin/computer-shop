package com.example.computershop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("HARD_DRIVE")
@Getter
@Setter
@NoArgsConstructor
public class HardDrive extends Product{

    private int capacity;

    public HardDrive(
            String serialNumber,
            String manufacturer,
            BigDecimal price,
            int quantity,
            int capacity
    ) {
        super(serialNumber, manufacturer, price, quantity);
        this.capacity = capacity;
    }
}
