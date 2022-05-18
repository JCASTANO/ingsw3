package com.uco.myproject.dominio.modelo;

import com.uco.myproject.dominio.servicio.PalindromeChecker;
import com.uco.myproject.dominio.validador.ValidadorArgumento;

public class Persona {

    private final String nombre;
    private final String apellido;

    public static Persona of(String nombre, String apellido) {

        ValidadorArgumento.validarObligatorio(nombre, "El nombre no puede ser vacio");
        ValidadorArgumento.validarObligatorio(apellido, "El apellido no puede ser vacio");

        return new Persona(nombre, apellido);
    }

    private Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public boolean tieneNombrePalindromo() {
        return PalindromeChecker.validate(this.nombre);
    }
}
