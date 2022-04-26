package com.uco.myproject.infraestructura.adaptador.entidad;

import javax.persistence.*;

@Entity
@Table(name = "rol_usuario")
public class EntidadRolUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String rol;

    public EntidadRolUsuario() {}

    public EntidadRolUsuario(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public Long getId() {
        return id;
    }
}
