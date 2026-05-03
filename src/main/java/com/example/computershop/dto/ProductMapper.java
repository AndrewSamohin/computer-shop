package com.example.computershop.dto;

import com.example.computershop.exception.InvalidProductTypeException;
import com.example.computershop.model.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Component
public class ProductMapper {

    private final Map<Class<? extends Product>, Function<Product, ProductDto>> toDtoMap;
    private final Map<Class<? extends Product>, BiConsumer<Product, ProductDto>> updateMap;
    private final ProductFactory productFactory;

    public ProductMapper(ProductFactory productFactory) {
        this.productFactory = productFactory;

        // Entity -> DTO
        toDtoMap = Map.of(
                DesktopComputer.class, product -> {
                    DesktopComputer pc = (DesktopComputer) product;
                    return ProductDto.builder()
                                     .id(pc.getId())
                                     .type("DESKTOP")
                                     .serialNumber(pc.getSerialNumber())
                                     .manufacturer(pc.getManufacturer())
                                     .price(pc.getPrice())
                                     .quantity(pc.getQuantity())
                                     .formFactor(pc.getFormFactor().name())
                                     .build();
                },
                Laptop.class, product -> {
                    Laptop laptop = (Laptop) product;
                    return ProductDto.builder()
                                     .id(laptop.getId())
                                     .type("LAPTOP")
                                     .serialNumber(laptop.getSerialNumber())
                                     .manufacturer(laptop.getManufacturer())
                                     .price(laptop.getPrice())
                                     .quantity(laptop.getQuantity())
                                     .screenSize(laptop.getScreenSize())
                                     .build();
                },
                Monitor.class, product -> {
                    Monitor monitor = (Monitor) product;
                    return ProductDto.builder()
                                     .id(monitor.getId())
                                     .type("MONITOR")
                                     .serialNumber(monitor.getSerialNumber())
                                     .manufacturer(monitor.getManufacturer())
                                     .price(monitor.getPrice())
                                     .quantity(monitor.getQuantity())
                                     .diagonal(monitor.getDiagonal())
                                     .build();
                },
                HardDrive.class, product -> {
                    HardDrive hd = (HardDrive) product;
                    return ProductDto.builder()
                                     .id(hd.getId())
                                     .type("HARD_DRIVE")
                                     .serialNumber(hd.getSerialNumber())
                                     .manufacturer(hd.getManufacturer())
                                     .price(hd.getPrice())
                                     .quantity(hd.getQuantity())
                                     .capacity(hd.getCapacity())
                                     .build();
                }
        );

        // Entity update (для updateProduct)
        updateMap = Map.of(
                DesktopComputer.class, (product, dto) -> {
                    DesktopComputer pc = (DesktopComputer) product;
                    if (dto.getFormFactor() != null) {
                        pc.setFormFactor(
                                DesktopComputer
                                        .FormFactor
                                        .valueOf(dto.getFormFactor()));
                    }
                },
                Laptop.class, (product, dto) -> {
                    Laptop laptop = (Laptop) product;
                    if (dto.getScreenSize() != null) {
                        laptop.setScreenSize(dto.getScreenSize());
                    }
                },
                Monitor.class, (product, dto) -> {
                    Monitor monitor = (Monitor) product;
                    if (dto.getDiagonal() != null) {
                        monitor.setDiagonal(dto.getDiagonal());
                    }
                },
                HardDrive.class, (product, dto) -> {
                    HardDrive hd = (HardDrive) product;
                    if (dto.getCapacity() != null) {
                        hd.setCapacity(dto.getCapacity());
                    }
                }
        );
    }

    public ProductDto toDto(Product product) {
        Function<Product, ProductDto> converter = toDtoMap.get(product.getClass());
        if (converter == null) {
            throw new InvalidProductTypeException(
                    "Неизвестный тип продукта: " + product.getClass().getSimpleName());
        }
        return converter.apply(product);
    }

    public Product toEntity(ProductDto dto) {
        return productFactory.create(dto.getType(), dto);
    }

    public void updateEntityFromDto(Product product, ProductDto dto) {
        BiConsumer<Product, ProductDto> updater = updateMap.get(product.getClass());
        if (updater != null) {
            updater.accept(product, dto);
        }
    }

}
