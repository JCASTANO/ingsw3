package com.uco.myproject.aplicacion.servicio;

import com.uco.myproject.aplicacion.dto.*;
import com.uco.myproject.dominio.modelo.Order;
import com.uco.myproject.dominio.servicio.ServicioCrearOrder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServicioAplicacionCrearOrder {

    private final ServicioCrearOrder servicioCrearOrder;

    public ServicioAplicacionCrearOrder(ServicioCrearOrder servicioCrearOrder) {
        this.servicioCrearOrder = servicioCrearOrder;
    }

    public DtoRespuesta<DtoOrderResponse> ejecutar(DtoOrderRequest request) {
        
        // Execute domain service with simplified input
        Order order = servicioCrearOrder.ejecutar(
            request.getCustomerId(), 
            request.getItems(),  // Pass the DTOs directly to domain service
            request.getPaymentMethod()
        );

        // Convert back to DTO
        DtoOrderResponse response = mapToResponse(order);
        
        return new DtoRespuesta<>(response);
    }

    private DtoOrderResponse mapToResponse(Order order) {
        List<DtoOrderItemResponse> itemResponses = order.getItems().stream()
            .map(item -> new DtoOrderItemResponse(item.getProductId(), item.getQuantity(), item.getUnitPrice()))
            .collect(Collectors.toList());

        return new DtoOrderResponse(
            order.getOrderId(),
            order.getCustomerId(),
            itemResponses,
            order.getTotalAmount(),
            order.getStatus().name(),
            order.getPaymentMethod()
        );
    }
}