package com.uco.myproject.dominio.modelo;

import java.math.BigDecimal;

public class Inventory {

    private final Long productId;
    private final Integer stock;
    private final BigDecimal unitPrice;

    public static Inventory of(Long productId, Integer stock, BigDecimal unitPrice) {
        if (productId == null) {
            throw new IllegalArgumentException("El productId no puede ser nulo");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("El stock debe ser mayor o igual a 0");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a 0");
        }
        
        return new Inventory(productId, stock, unitPrice);
    }

    private Inventory(Long productId, Integer stock, BigDecimal unitPrice) {
        this.productId = productId;
        this.stock = stock;
        this.unitPrice = unitPrice;
    }

    public boolean hasStock(Integer requiredQuantity) {
        return this.stock >= requiredQuantity;
    }

    public Inventory reduceStock(Integer quantity) {
        if (quantity > this.stock) {
            throw new IllegalStateException("Stock insuficiente para el producto " + productId);
        }
        return new Inventory(this.productId, this.stock - quantity, this.unitPrice);
    }

    public Long getProductId() { return productId; }
    public Integer getStock() { return stock; }
    public BigDecimal getUnitPrice() { return unitPrice; }
}