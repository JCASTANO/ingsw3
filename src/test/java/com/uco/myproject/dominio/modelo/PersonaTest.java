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
    void validarCamposFaltantes() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = null;
        String apellido = "castaño";

        //act - assert (ejecuta el metodo a probar)

        Assertions.assertEquals("El nombre no puede ser vacio",Assertions.assertThrows(IllegalArgumentException.class, () ->
            Persona.of(nombre, apellido)
        ).getMessage());
    }

    @Test
    void validarCamposVacios() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = "";
        String apellido = "castaño";

        //act - assert (ejecuta el metodo a probar)

        Assertions.assertEquals("El nombre no puede ser vacio",Assertions.assertThrows(IllegalArgumentException.class, () ->
                Persona.of(nombre, apellido)
        ).getMessage());
    }

    @Test
    void validarCreacionExitosaConEdad() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = "juan";
        String apellido = "castaño";
        Integer edad = 25;

        //act (ejecuta el metodo a probar)
        Persona persona = Persona.of(nombre, apellido, edad);

        //assert se valida el resultado
        Assertions.assertEquals("juan", persona.getNombre());
        Assertions.assertEquals("castaño", persona.getApellido());
        Assertions.assertEquals(25, persona.getEdad());
    }

    @Test
    void validarEdadEnRangoMinimo() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = "juan";
        String apellido = "castaño";
        Integer edad = 0;

        //act (ejecuta el metodo a probar)
        Persona persona = Persona.of(nombre, apellido, edad);

        //assert se valida el resultado
        Assertions.assertEquals(0, persona.getEdad());
    }

    @Test
    void validarEdadEnRangoMaximo() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = "juan";
        String apellido = "castaño";
        Integer edad = 100;

        //act (ejecuta el metodo a probar)
        Persona persona = Persona.of(nombre, apellido, edad);

        //assert se valida el resultado
        Assertions.assertEquals(100, persona.getEdad());
    }

    @Test
    void validarEdadFueraRangoMinimo() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = "juan";
        String apellido = "castaño";
        Integer edad = -1;

        //act - assert (ejecuta el metodo a probar)
        Assertions.assertEquals("La edad debe estar entre 0 y 100", Assertions.assertThrows(IllegalArgumentException.class, () ->
                Persona.of(nombre, apellido, edad)
        ).getMessage());
    }

    @Test
    void validarEdadFueraRangoMaximo() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = "juan";
        String apellido = "castaño";
        Integer edad = 101;

        //act - assert (ejecuta el metodo a probar)
        Assertions.assertEquals("La edad debe estar entre 0 y 100", Assertions.assertThrows(IllegalArgumentException.class, () ->
                Persona.of(nombre, apellido, edad)
        ).getMessage());
    }

    @Test
    void validarCreacionSinEdad() {
        //patron 3a

        //arrange (prepara todos los datos para la prueba)
        String nombre = "juan";
        String apellido = "castaño";

        //act (ejecuta el metodo a probar)
        Persona persona = Persona.of(nombre, apellido);

        //assert se valida el resultado
        Assertions.assertEquals("juan", persona.getNombre());
        Assertions.assertEquals("castaño", persona.getApellido());
        Assertions.assertNull(persona.getEdad());
    }
}
