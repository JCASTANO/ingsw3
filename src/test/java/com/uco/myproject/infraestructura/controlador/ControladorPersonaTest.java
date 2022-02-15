package com.uco.myproject.infraestructura.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uco.myproject.aplicacion.dto.DtoPersona;
import com.uco.myproject.aplicacion.dto.DtoRespuesta;
import com.uco.myproject.dominio.puerto.RepositorioPersona;
import com.uco.myproject.infraestructura.ApplicationMock;
import com.uco.myproject.infraestructura.testdatabuilder.DtoPersonaTestDataBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ControladorPersona.class)
@ContextConfiguration(classes = ApplicationMock.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ControladorPersonaTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mocMvc;

    @Autowired
    RepositorioPersona repositorioPersona;

    @Test
    @DisplayName("Debe crear una persona de forma exitosa y luego fallar al crear la misma")
    void crearDuplicadaTest() throws Exception {

        crear();

        // arrange
        var dto = new DtoPersonaTestDataBuilder().build();

        // act - assert
        mocMvc.perform(MockMvcRequestBuilders.post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isConflict());
    }


    @Test
    @DisplayName("Debe crear una persona de forma exitosa y validar que si quedó guardada")
    void crearTest() throws Exception {

        crear();
    }

    private void crear() throws Exception {
        // arrange
        var dto = new DtoPersonaTestDataBuilder().build();

        // act
        var result = mocMvc.perform(MockMvcRequestBuilders.post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andReturn();

        //assert
        var jsonResult = result.getResponse().getContentAsString();
        DtoRespuesta<Integer> respuesta = objectMapper.readValue(jsonResult, DtoRespuesta.class);

        Long id = respuesta.getValor().longValue();
        Assertions.assertNotNull(id);

        var persona = repositorioPersona.consultarPorId(id);

        Assertions.assertEquals(dto.getNombre(), persona.getNombre());
        Assertions.assertEquals(dto.getApellido(), persona.getApellido());
    }

    @Test
    @DisplayName("Debe listar las personas")
    void listarTest() throws Exception {
        mocMvc.perform(get("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
