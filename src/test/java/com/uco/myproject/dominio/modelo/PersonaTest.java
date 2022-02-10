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
        Persona persona = new Persona(nombre, apellido);

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

        //act (ejecuta el metodo a probar)
        try {
            new Persona(nombre, apellido);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            //assert
            Assertions.assertEquals(e.getMessage(), "El nombre no puede ser vacio");
        }
    }
}
