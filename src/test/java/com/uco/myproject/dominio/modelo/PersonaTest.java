package com.uco.myproject.dominio.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonaTest {

    @Test
    public void validarCreacionExitosa() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = "juan";
        String apellido = "castaño";

        //act (ejecuta el metodo a probar)
        Persona persona = Persona.of(nombre, apellido);

        //assert se valida el resultado

        Assertions.assertEquals(persona.getNombre(), "juan");
        Assertions.assertEquals(persona.getApellido(), "castaño");
    }

    @Test
    public void validarCamposFaltantes() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = null;
        String apellido = "castaño";

        //act - assert (ejecuta el metodo a probar)

        Assertions.assertEquals(Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Persona.of(nombre, apellido);
        }).getMessage(), "El nombre no puede ser vacio");
    }
}
