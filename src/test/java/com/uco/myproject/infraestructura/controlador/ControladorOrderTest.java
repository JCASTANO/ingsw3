package com.uco.myproject.infraestructura.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uco.myproject.aplicacion.dto.DtoOrderItemRequest;
import com.uco.myproject.aplicacion.dto.DtoOrderRequest;
import com.uco.myproject.aplicacion.dto.DtoOrderResponse;
import com.uco.myproject.infraestructura.ApplicationMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = ApplicationMock.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ControladorOrderTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Debe crear una orden exitosa y luego consultarla")
    void crearYConsultarOrderTest() throws Exception {
        // arrange
        DtoOrderRequest orderRequest = new DtoOrderRequest(
            123L,
            Arrays.asList(
                new DtoOrderItemRequest(456L, 2),
                new DtoOrderItemRequest(789L, 1)
            ),
            "CREDIT_CARD"
        );

        // act - crear orden
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest))
                )
                .andExpect(status().isCreated())
                .andReturn();

        // assert - verificar respuesta de creación
        var jsonResult = result.getResponse().getContentAsString();
        DtoOrderResponse orderResponse = objectMapper.readValue(jsonResult, DtoOrderResponse.class);

        // act - consultar orden creada
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/" + orderResponse.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", is(orderResponse.getOrderId().intValue())))
                .andExpect(jsonPath("$.customerId", is(123)))
                .andExpect(jsonPath("$.status", is("PAID")))
                .andExpect(jsonPath("$.totalAmount", is(450.00)))
                .andExpect(jsonPath("$.items[0].productId", is(456)))
                .andExpect(jsonPath("$.items[0].quantity", is(2)))
                .andExpect(jsonPath("$.items[1].productId", is(789)))
                .andExpect(jsonPath("$.items[1].quantity", is(1)));
    }

    @Test
    @DisplayName("Debe fallar con cantidad excesiva por producto")
    void fallarConCantidadExcesivaTest() throws Exception {
        // arrange
        DtoOrderRequest orderRequest = new DtoOrderRequest(
            123L,
            Arrays.asList(
                new DtoOrderItemRequest(456L, 15) // Exceeds limit of 10
            ),
            "CREDIT_CARD"
        );

        // act - assert
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe fallar con método de pago no permitido")
    void fallarConMetodoPagoNoPermitidoTest() throws Exception {
        // arrange
        DtoOrderRequest orderRequest = new DtoOrderRequest(
            123L,
            Arrays.asList(
                new DtoOrderItemRequest(456L, 2)
            ),
            "DEBIT_CARD" // Not allowed payment method
        );

        // act - assert
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe fallar con stock insuficiente")
    void fallarConStockInsuficienteTest() throws Exception {
        // arrange
        DtoOrderRequest orderRequest = new DtoOrderRequest(
            123L,
            Arrays.asList(
                new DtoOrderItemRequest(789L, 5) // Only 2 available in test data
            ),
            "CREDIT_CARD"
        );

        // act - assert
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest))
                )
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Debe fallar al consultar orden inexistente")
    void fallarAlConsultarOrdenInexistenteTest() throws Exception {
        // act - assert
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Orden no encontrada")));
    }
}