package com.uco.myproject.infraestructura.testdatabuilder;

import com.uco.myproject.aplicacion.dto.DtoPersona;

public class DtoPersonaTestDataBuilder {

    private String nombre;
    private String apellido;

    public DtoPersonaTestDataBuilder() {
        this.nombre = "juan";
        this.apellido = "castaño";
    }

    public DtoPersonaTestDataBuilder conNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public DtoPersonaTestDataBuilder conApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public DtoPersona build() {
        return new DtoPersona(nombre, apellido);
    }
}
