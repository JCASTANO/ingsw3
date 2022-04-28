package com.uco.myproject.dominio.dto;

import java.util.List;

public class DtoUsuarioActual {

    private String usuario;
    private List<String> roles;

    public DtoUsuarioActual() {}

    public DtoUsuarioActual(String usuario, List<String> roles) {
        this.usuario = usuario;
        this.roles = roles;
    }

    public String getUsuario() {
        return usuario;
    }

    public List<String> getRoles() {
        return roles;
    }
}
