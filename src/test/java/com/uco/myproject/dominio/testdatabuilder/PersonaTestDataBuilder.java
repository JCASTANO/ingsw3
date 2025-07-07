package com.uco.myproject.dominio.testdatabuilder;

import com.uco.myproject.dominio.modelo.Persona;

public class PersonaTestDataBuilder {

    private String nombre;
    private String apellido;
    private Integer edad;

    public PersonaTestDataBuilder() {
        this.nombre = "juan";
        this.apellido = "castano";
        this.edad = 25;
    }

    public PersonaTestDataBuilder conNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public PersonaTestDataBuilder conApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public PersonaTestDataBuilder conEdad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public Persona build() {
        return Persona.of(nombre, apellido, edad);
    }
}
