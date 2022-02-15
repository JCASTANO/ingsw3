package com.uco.myproject.dominio.servicio;

import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.puerto.RepositorioPersona;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ServicioGuardarPersonaTest {

    @Test
    void siNombreYaExisteDeberiaRetornarError() {


        var persona = Persona.of("juan", "castano");

        var repositorio = Mockito.mock(RepositorioPersona.class);
        var servicio = new ServicioGuardarPersona(repositorio);

        Mockito.when(repositorio.existe(Mockito.any())).thenReturn(true);

        Assertions.assertEquals("Ya existe la persona con los datos ingresados",Assertions.assertThrows(IllegalStateException.class, () ->
            servicio.ejecutar(persona)
        ).getMessage());

    }
}
