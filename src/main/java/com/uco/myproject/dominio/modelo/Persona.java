package com.uco.myproject.dominio.modelo;

import com.uco.myproject.dominio.servicio.PalindromeChecker;
import com.uco.myproject.dominio.validador.ValidadorArgumento;

public class Persona {

    private final String nombre;
    private final String apellido;
    private final Integer edad;

    public static Persona of(String nombre, String apellido) {
        return of(nombre, apellido, null);
    }

    public static Persona of(String nombre, String apellido, Integer edad) {

        ValidadorArgumento.validarObligatorio(nombre, "El nombre no puede ser vacio");
        ValidadorArgumento.validarObligatorio(apellido, "El apellido no puede ser vacio");
        ValidadorArgumento.validarRangoEntero(edad, 0, 100, "La edad debe estar entre 0 y 100");

        return new Persona(nombre, apellido, edad);
    }

    private Persona(String nombre, String apellido, Integer edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public boolean tieneNombrePalindromo() {
        return PalindromeChecker.validate(this.nombre);
    }
}
