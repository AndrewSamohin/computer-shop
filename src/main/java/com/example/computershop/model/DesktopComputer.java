package com.example.computershop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("DESKTOP")
@Getter
@Setter
@NoArgsConstructor
public class DesktopComputer extends Product{

    public enum FormFactor {
        DESKTOP,
        NETTOP,
        ALL_IN_ONE
    }

    @Enumerated(EnumType.STRING)
    private FormFactor formFactor;


    public DesktopComputer(
            String serialNumber,
            String manufacturer,
            BigDecimal price,
            int quantity,
            FormFactor formFactor
    ) {
        super(serialNumber, manufacturer, price, quantity);
        this.formFactor = formFactor;
    }
}
