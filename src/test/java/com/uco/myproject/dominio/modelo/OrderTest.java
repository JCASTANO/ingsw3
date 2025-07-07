package com.uco.myproject.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void crearOrderValida() {
        // arrange
        List<OrderItem> items = Arrays.asList(
            OrderItem.of(456L, 2, new BigDecimal("100.00")),
            OrderItem.of(789L, 1, new BigDecimal("250.00"))
        );
        Long customerId = 123L;
        String paymentMethod = "CREDIT_CARD";

        // act
        Order order = Order.of(customerId, items, paymentMethod);

        // assert
        assertEquals(customerId, order.getCustomerId());
        assertEquals(items, order.getItems());
        assertEquals(paymentMethod, order.getPaymentMethod());
        assertEquals(new BigDecimal("450.00"), order.getTotalAmount());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertNull(order.getOrderId());
    }

    @Test
    void fallarConCantidadExcesiva() {
        // arrange
        List<OrderItem> items = Arrays.asList(
            OrderItem.of(456L, 15, new BigDecimal("100.00")) // Exceeds limit
        );
        Long customerId = 123L;
        String paymentMethod = "CREDIT_CARD";

        // act & assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            Order.of(customerId, items, paymentMethod)
        );
        assertEquals("La cantidad no puede exceder 10 unidades por producto", exception.getMessage());
    }

    @Test
    void fallarConMetodoPagoInvalido() {
        // arrange
        List<OrderItem> items = Arrays.asList(
            OrderItem.of(456L, 2, new BigDecimal("100.00"))
        );
        Long customerId = 123L;
        String paymentMethod = "DEBIT_CARD";

        // act & assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            Order.of(customerId, items, paymentMethod)
        );
        assertEquals("Medio de pago no permitido", exception.getMessage());
    }

    @Test
    void cambiarEstadoOrder() {
        // arrange
        List<OrderItem> items = Arrays.asList(
            OrderItem.of(456L, 2, new BigDecimal("100.00"))
        );
        Order order = Order.of(123L, items, "CREDIT_CARD");

        // act
        Order paidOrder = order.withStatus(OrderStatus.PAID);

        // assert
        assertEquals(OrderStatus.PAID, paidOrder.getStatus());
        assertEquals(OrderStatus.PENDING, order.getStatus()); // Original unchanged
    }

    @Test
    void asignarIdAOrder() {
        // arrange
        List<OrderItem> items = Arrays.asList(
            OrderItem.of(456L, 2, new BigDecimal("100.00"))
        );
        Order order = Order.of(123L, items, "CREDIT_CARD");

        // act
        Order orderWithId = order.withId(1L);

        // assert
        assertEquals(1L, orderWithId.getOrderId());
        assertNull(order.getOrderId()); // Original unchanged
    }
}