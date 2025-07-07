package com.uco.myproject.dominio.servicio;

import com.uco.myproject.aplicacion.dto.DtoOrderItemRequest;
import com.uco.myproject.dominio.modelo.*;
import com.uco.myproject.dominio.puerto.RepositorioInventory;
import com.uco.myproject.dominio.puerto.RepositorioOrder;
import com.uco.myproject.dominio.puerto.ServicioPayment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioCrearOrder {

    private final RepositorioOrder repositorioOrder;
    private final RepositorioInventory repositorioInventory;
    private final ServicioPayment servicioPayment;

    public ServicioCrearOrder(RepositorioOrder repositorioOrder, 
                             RepositorioInventory repositorioInventory,
                             ServicioPayment servicioPayment) {
        this.repositorioOrder = repositorioOrder;
        this.repositorioInventory = repositorioInventory;
        this.servicioPayment = servicioPayment;
    }

    public Order ejecutar(Long customerId, List<DtoOrderItemRequest> requestItems, String paymentMethod) {
        
        // 1. Validate basic input
        if (customerId == null) {
            throw new IllegalArgumentException("El customerId no puede ser nulo");
        }
        if (requestItems == null || requestItems.isEmpty()) {
            throw new IllegalArgumentException("Los items no pueden estar vacíos");
        }
        if (!"CREDIT_CARD".equals(paymentMethod)) {
            throw new IllegalArgumentException("Medio de pago no permitido");
        }
        
        // 2. Validate and prepare order items with prices
        List<OrderItem> orderItems = validarYPrepararItems(requestItems);
        
        // 3. Create order (this validates business rules)
        Order order = Order.of(customerId, orderItems, paymentMethod);
        
        // 4. Check stock availability
        validarStock(orderItems);
        
        // 5. Process payment
        boolean paymentSuccess = servicioPayment.procesarPago(order.getTotalAmount(), paymentMethod);
        
        if (!paymentSuccess) {
            // Save order as FAILED and return with payment error
            Order failedOrder = order.withStatus(OrderStatus.FAILED);
            Long orderId = repositorioOrder.guardar(failedOrder);
            throw new PaymentFailedException("Error procesando el pago");
        }
        
        // 6. Reserve stock
        try {
            reservarStock(orderItems);
        } catch (Exception e) {
            // Save order as FAILED if stock reservation fails after successful payment
            Order failedOrder = order.withStatus(OrderStatus.FAILED);
            Long orderId = repositorioOrder.guardar(failedOrder);
            throw new StockReservationException("Error reservando el stock");
        }
        
        // 7. Save order as PAID
        Order paidOrder = order.withStatus(OrderStatus.PAID);
        Long orderId = repositorioOrder.guardar(paidOrder);
        
        return paidOrder.withId(orderId);
    }

    private List<OrderItem> validarYPrepararItems(List<DtoOrderItemRequest> requestItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        
        for (DtoOrderItemRequest item : requestItems) {
            // Validate quantity limits
            if (item.getQuantity() > 10) {
                throw new IllegalArgumentException("La cantidad no puede exceder 10 unidades por producto");
            }
            
            // Check if product exists and get current price
            Inventory inventory = repositorioInventory.consultarPorProductId(item.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado: " + item.getProductId()));
            
            // Create order item with current price
            OrderItem orderItem = OrderItem.of(item.getProductId(), item.getQuantity(), inventory.getUnitPrice());
            orderItems.add(orderItem);
        }
        
        return orderItems;
    }

    private void validarStock(List<OrderItem> items) {
        for (OrderItem item : items) {
            Inventory inventory = repositorioInventory.consultarPorProductId(item.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado: " + item.getProductId()));
            
            if (!inventory.hasStock(item.getQuantity())) {
                throw new InsufficientStockException("Stock insuficiente para el producto " + item.getProductId());
            }
        }
    }

    private void reservarStock(List<OrderItem> items) {
        for (OrderItem item : items) {
            Inventory inventory = repositorioInventory.consultarPorProductId(item.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado: " + item.getProductId()));
            
            Inventory updatedInventory = inventory.reduceStock(item.getQuantity());
            repositorioInventory.actualizarStock(updatedInventory);
        }
    }

    // Business exceptions
    public static class PaymentFailedException extends RuntimeException {
        public PaymentFailedException(String message) { super(message); }
    }

    public static class InsufficientStockException extends RuntimeException {
        public InsufficientStockException(String message) { super(message); }
    }

    public static class StockReservationException extends RuntimeException {
        public StockReservationException(String message) { super(message); }
    }

    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) { super(message); }
    }
}