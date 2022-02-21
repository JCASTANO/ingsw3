package com.uco.myproject.dominio.modelo;

public class Persona {

    private final String nombre;
    private final String apellido;

    public static Persona of(String nombre, String apellido) {

        validarObligatorio(nombre, "El nombre no puede ser vacio");
        validarObligatorio(apellido, "El apellido no puede ser vacio");

        return new Persona(nombre, apellido);
    }

    private Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    private static void validarObligatorio(String valor, String mensaje) {
        if(valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}
