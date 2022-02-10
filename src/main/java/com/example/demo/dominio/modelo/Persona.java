package com.example.demo.dominio.modelo;

public class Persona {

    private String nombre;
    private String apellido;

    public Persona(String nombre, String apellido) {

        validarObligatorio(nombre, "El nombre no puede ser vacio");
        validarObligatorio(apellido, "El apellido no puede ser vacio");

        this.nombre = nombre;
        this.apellido = apellido;
    }

    private void validarObligatorio(String valor, String mensaje) {
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
