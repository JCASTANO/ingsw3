package com.uco.myproject.dominio.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PersonaTest {

    @Test
    void validarCreacionExitosa() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = "juan";
        String apellido = "castaño";

        //act (ejecuta el metodo a probar)
        Persona persona = Persona.of(nombre, apellido);

        //assert se valida el resultado

        Assertions.assertEquals("juan",persona.getNombre());
        Assertions.assertEquals("castaño",persona.getApellido());
    }

    @Test
    public void validarCamposFaltantes() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = null;
        String apellido = "castaño";

        //act - assert (ejecuta el metodo a probar)

        Assertions.assertEquals("El nombre no puede ser vacio",Assertions.assertThrows(IllegalArgumentException.class, () ->
            Persona.of(nombre, apellido)
        ).getMessage());
    }
}
