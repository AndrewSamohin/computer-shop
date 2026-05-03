package com.example.computershop.dto;

import com.example.computershop.exception.InvalidProductTypeException;
import com.example.computershop.model.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class ProductFactory {

    private final Map<String, Function<ProductDto, Product>> factoryMap;
    private final Map<String, Class<? extends Product>> classMap;

    public ProductFactory() {
        factoryMap = Map.of(
                "DESKTOP", dto -> {
                    DesktopComputer.FormFactor ff = DesktopComputer.FormFactor.valueOf(dto.getFormFactor());
                    return new DesktopComputer(
                            dto.getSerialNumber(), dto.getManufacturer(),
                            dto.getPrice(), dto.getQuantity(), ff);
                },
                "LAPTOP", dto -> new Laptop(
                        dto.getSerialNumber(), dto.getManufacturer(),
                        dto.getPrice(), dto.getQuantity(), dto.getScreenSize()),
                "MONITOR", dto -> new Monitor(
                        dto.getSerialNumber(), dto.getManufacturer(),
                        dto.getPrice(), dto.getQuantity(), dto.getDiagonal()),
                "HARD_DRIVE", dto -> new HardDrive(
                        dto.getSerialNumber(), dto.getManufacturer(),
                        dto.getPrice(), dto.getQuantity(), dto.getCapacity())
        );

        classMap = Map.of(
                "DESKTOP", DesktopComputer.class,
                "LAPTOP", Laptop.class,
                "MONITOR", Monitor.class,
                "HARD_DRIVE", HardDrive.class
        );
    }

    public Product create(String type, ProductDto dto) {
        Function<ProductDto, Product> constructor =
                factoryMap.get(type.toUpperCase());
        if (constructor == null) {
            throw new InvalidProductTypeException(type);
        }
        return constructor.apply(dto);
    }

    public Class<? extends Product> resolveClass(String type) {
        Class<? extends Product> clazz = classMap.get(type.toUpperCase());
        if (clazz == null) {
            throw new InvalidProductTypeException(type);
        }
        return clazz;
    }

}
