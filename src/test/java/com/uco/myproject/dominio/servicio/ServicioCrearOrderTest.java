package com.uco.myproject.dominio.servicio;

import com.uco.myproject.aplicacion.dto.DtoOrderItemRequest;
import com.uco.myproject.dominio.modelo.*;
import com.uco.myproject.dominio.puerto.RepositorioInventory;
import com.uco.myproject.dominio.puerto.RepositorioOrder;
import com.uco.myproject.dominio.puerto.ServicioPayment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ServicioCrearOrderTest {

    @Test
    void crearOrderExitoso() {
        // arrange
        var repositorioOrder = Mockito.mock(RepositorioOrder.class);
        var repositorioInventory = Mockito.mock(RepositorioInventory.class);
        var servicioPayment = Mockito.mock(ServicioPayment.class);
        var servicio = new ServicioCrearOrder(repositorioOrder, repositorioInventory, servicioPayment);

        Long customerId = 123L;
        List<DtoOrderItemRequest> items = Arrays.asList(
            new DtoOrderItemRequest(456L, 2),
            new DtoOrderItemRequest(789L, 1)
        );
        String paymentMethod = "CREDIT_CARD";

        // Mock inventory
        Inventory inventory456 = Inventory.of(456L, 5, new BigDecimal("100.00"));
        Inventory inventory789 = Inventory.of(789L, 2, new BigDecimal("250.00"));
        
        Mockito.when(repositorioInventory.consultarPorProductId(456L))
               .thenReturn(Optional.of(inventory456));
        Mockito.when(repositorioInventory.consultarPorProductId(789L))
               .thenReturn(Optional.of(inventory789));

        // Mock payment success
        Mockito.when(servicioPayment.procesarPago(Mockito.any(), Mockito.eq(paymentMethod)))
               .thenReturn(true);

        // Mock order save
        Mockito.when(repositorioOrder.guardar(Mockito.any())).thenReturn(1L);

        // act
        Order result = servicio.ejecutar(customerId, items, paymentMethod);

        // assert
        Assertions.assertEquals(1L, result.getOrderId());
        Assertions.assertEquals(customerId, result.getCustomerId());
        Assertions.assertEquals(OrderStatus.PAID, result.getStatus());
        Assertions.assertEquals(new BigDecimal("450.00"), result.getTotalAmount());
        
        Mockito.verify(repositorioInventory, Mockito.times(2)).actualizarStock(Mockito.any());
        Mockito.verify(repositorioOrder, Mockito.times(1)).guardar(Mockito.any());
    }

    @Test
    void stockInsuficienteDeberiaRetornarError() {
        // arrange
        var repositorioOrder = Mockito.mock(RepositorioOrder.class);
        var repositorioInventory = Mockito.mock(RepositorioInventory.class);
        var servicioPayment = Mockito.mock(ServicioPayment.class);
        var servicio = new ServicioCrearOrder(repositorioOrder, repositorioInventory, servicioPayment);

        Long customerId = 123L;
        List<DtoOrderItemRequest> items = Arrays.asList(
            new DtoOrderItemRequest(456L, 10) // Request more than available stock
        );
        String paymentMethod = "CREDIT_CARD";

        // Mock inventory with insufficient stock
        Inventory inventory = Inventory.of(456L, 5, new BigDecimal("100.00"));
        
        Mockito.when(repositorioInventory.consultarPorProductId(456L))
               .thenReturn(Optional.of(inventory));

        // act - assert
        Assertions.assertEquals("Stock insuficiente para el producto 456",
                Assertions.assertThrows(ServicioCrearOrder.InsufficientStockException.class, () ->
            servicio.ejecutar(customerId, items, paymentMethod)
        ).getMessage());
    }

    @Test
    void pagoFallidoDeberiaRetornarError() {
        // arrange
        var repositorioOrder = Mockito.mock(RepositorioOrder.class);
        var repositorioInventory = Mockito.mock(RepositorioInventory.class);
        var servicioPayment = Mockito.mock(ServicioPayment.class);
        var servicio = new ServicioCrearOrder(repositorioOrder, repositorioInventory, servicioPayment);

        Long customerId = 123L;
        List<DtoOrderItemRequest> items = Arrays.asList(
            new DtoOrderItemRequest(456L, 2)
        );
        String paymentMethod = "CREDIT_CARD";

        // Mock inventory
        Inventory inventory = Inventory.of(456L, 5, new BigDecimal("100.00"));
        
        Mockito.when(repositorioInventory.consultarPorProductId(456L))
               .thenReturn(Optional.of(inventory));

        // Mock payment failure
        Mockito.when(servicioPayment.procesarPago(Mockito.any(), Mockito.eq(paymentMethod)))
               .thenReturn(false);

        // Mock order save for failed order
        Mockito.when(repositorioOrder.guardar(Mockito.any())).thenReturn(1L);

        // act - assert
        Assertions.assertEquals("Error procesando el pago",
                Assertions.assertThrows(ServicioCrearOrder.PaymentFailedException.class, () ->
            servicio.ejecutar(customerId, items, paymentMethod)
        ).getMessage());
        
        // Verify failed order was saved
        Mockito.verify(repositorioOrder, Mockito.times(1)).guardar(Mockito.any());
    }
}