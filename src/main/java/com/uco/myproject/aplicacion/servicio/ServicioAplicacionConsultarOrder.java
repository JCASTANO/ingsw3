package com.uco.myproject.aplicacion.servicio;

import com.uco.myproject.aplicacion.dto.DtoOrderItemResponse;
import com.uco.myproject.aplicacion.dto.DtoOrderResponse;
import com.uco.myproject.dominio.modelo.Order;
import com.uco.myproject.dominio.servicio.ServicioConsultarOrder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ServicioAplicacionConsultarOrder {

    private final ServicioConsultarOrder servicioConsultarOrder;

    public ServicioAplicacionConsultarOrder(ServicioConsultarOrder servicioConsultarOrder) {
        this.servicioConsultarOrder = servicioConsultarOrder;
    }

    public Optional<DtoOrderResponse> ejecutar(Long orderId) {
        Optional<Order> order = servicioConsultarOrder.ejecutar(orderId);
        
        return order.map(this::mapToResponse);
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