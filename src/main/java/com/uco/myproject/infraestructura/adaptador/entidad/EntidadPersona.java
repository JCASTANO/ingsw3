package com.uco.myproject.infraestructura.adaptador.entidad;

import javax.persistence.*;

@Entity
@Table(name = "persona")
public class EntidadPersona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;
    private String apellido;
    private Integer edad;

    public EntidadPersona() {}

    public EntidadPersona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = null;
    }

    public EntidadPersona(String nombre, String apellido, Integer edad) {
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

    public Long getId() {
        return id;
    }
}
