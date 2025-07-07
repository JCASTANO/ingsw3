package com.uco.myproject.dominio.modelo;

import com.uco.myproject.dominio.validador.ValidadorArgumento;

import java.math.BigDecimal;

public class OrderItem {

    private final Long productId;
    private final Integer quantity;
    private final BigDecimal unitPrice;

    public static OrderItem of(Long productId, Integer quantity, BigDecimal unitPrice) {
        if (productId == null) {
            throw new IllegalArgumentException("El productId no puede ser nulo");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("La cantidad no puede ser nula");
        }
        if (unitPrice == null) {
            throw new IllegalArgumentException("El precio unitario no puede ser nulo");
        }
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        
        if (unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a 0");
        }
        
        return new OrderItem(productId, quantity, unitPrice);
    }

    private OrderItem(Long productId, Integer quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
}