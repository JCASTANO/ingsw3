package com.uco.myproject.dominio.modelo;

import com.uco.myproject.dominio.validador.ValidadorArgumento;

import java.math.BigDecimal;
import java.util.List;

public class Order {

    private final Long orderId;
    private final Long customerId;
    private final List<OrderItem> items;
    private final BigDecimal totalAmount;
    private final OrderStatus status;
    private final String paymentMethod;

    public static Order of(Long customerId, List<OrderItem> items, String paymentMethod) {
        if (customerId == null) {
            throw new IllegalArgumentException("El customerId no puede ser nulo");
        }
        if (items == null) {
            throw new IllegalArgumentException("Los items no pueden ser nulos");
        }
        ValidadorArgumento.validarObligatorio(paymentMethod, "El método de pago no puede ser nulo");
        
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Los items no pueden estar vacíos");
        }
        
        if (!"CREDIT_CARD".equals(paymentMethod)) {
            throw new IllegalArgumentException("Medio de pago no permitido");
        }
        
        // Validate quantity limits
        for (OrderItem item : items) {
            if (item.getQuantity() > 10) {
                throw new IllegalArgumentException("La cantidad no puede exceder 10 unidades por producto");
            }
        }
        
        BigDecimal total = calculateTotal(items);
        
        return new Order(null, customerId, items, total, OrderStatus.PENDING, paymentMethod);
    }

    public static Order withId(Long orderId, Long customerId, List<OrderItem> items, 
                              BigDecimal totalAmount, OrderStatus status, String paymentMethod) {
        return new Order(orderId, customerId, items, totalAmount, status, paymentMethod);
    }

    private Order(Long orderId, Long customerId, List<OrderItem> items, 
                  BigDecimal totalAmount, OrderStatus status, String paymentMethod) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    private static BigDecimal calculateTotal(List<OrderItem> items) {
        return items.stream()
                   .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order withStatus(OrderStatus newStatus) {
        return new Order(this.orderId, this.customerId, this.items, 
                        this.totalAmount, newStatus, this.paymentMethod);
    }

    public Order withId(Long newOrderId) {
        return new Order(newOrderId, this.customerId, this.items, 
                        this.totalAmount, this.status, this.paymentMethod);
    }

    // Getters
    public Long getOrderId() { return orderId; }
    public Long getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
}