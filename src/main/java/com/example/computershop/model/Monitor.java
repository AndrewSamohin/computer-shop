package com.example.computershop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("MONITOR")
@Getter
@Setter
@NoArgsConstructor
public class Monitor extends Product{

    private double diagonal;

    public Monitor(
            String serialNumber,
            String manufacturer,
            BigDecimal price,
            int quantity,
            double diagonal
    ) {
        super(serialNumber, manufacturer, price, quantity);
        this.diagonal = diagonal;
    }
}
