package com.uco.myproject.infraestructura.controlador;

import com.uco.myproject.aplicacion.dto.*;
import com.uco.myproject.aplicacion.servicio.ServicioAplicacionConsultarOrder;
import com.uco.myproject.aplicacion.servicio.ServicioAplicacionCrearOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ControladorOrderTest {

    @Mock
    private ServicioAplicacionCrearOrder servicioCrearOrder;

    @Mock
    private ServicioAplicacionConsultarOrder servicioConsultarOrder;

    @InjectMocks
    private ControladorOrder controladorOrder;

    @Test
    void crearOrderExitoso() {
        // arrange
        DtoOrderRequest request = new DtoOrderRequest(
            123L,
            Arrays.asList(
                new DtoOrderItemRequest(456L, 2),
                new DtoOrderItemRequest(789L, 1)
            ),
            "CREDIT_CARD"
        );

        DtoOrderResponse expectedResponse = new DtoOrderResponse(
            1L, 
            123L,
            Arrays.asList(
                new DtoOrderItemResponse(456L, 2, new BigDecimal("100.00")),
                new DtoOrderItemResponse(789L, 1, new BigDecimal("250.00"))
            ),
            new BigDecimal("450.00"),
            "PAID",
            "CREDIT_CARD"
        );

        Mockito.when(servicioCrearOrder.ejecutar(request))
               .thenReturn(new DtoRespuesta<>(expectedResponse));

        // act
        ResponseEntity<DtoOrderResponse> response = controladorOrder.crear(request);

        // assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        Mockito.verify(servicioCrearOrder, Mockito.times(1)).ejecutar(request);
    }

    @Test
    void consultarOrderExistente() {
        // arrange
        Long orderId = 1L;
        DtoOrderResponse expectedResponse = new DtoOrderResponse(
            1L, 
            123L,
            Arrays.asList(
                new DtoOrderItemResponse(456L, 2, new BigDecimal("100.00"))
            ),
            new BigDecimal("200.00"),
            "PAID",
            "CREDIT_CARD"
        );

        Mockito.when(servicioConsultarOrder.ejecutar(orderId))
               .thenReturn(Optional.of(expectedResponse));

        // act
        ResponseEntity<?> response = controladorOrder.consultar(orderId);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        Mockito.verify(servicioConsultarOrder, Mockito.times(1)).ejecutar(orderId);
    }

    @Test
    void consultarOrderInexistente() {
        // arrange
        Long orderId = 999L;

        Mockito.when(servicioConsultarOrder.ejecutar(orderId))
               .thenReturn(Optional.empty());

        // act
        ResponseEntity<?> response = controladorOrder.consultar(orderId);

        // assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        
        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Orden no encontrada", body.get("message"));
        
        Mockito.verify(servicioConsultarOrder, Mockito.times(1)).ejecutar(orderId);
    }
}