package com.uco.myproject.dominio.modelo;

import com.uco.myproject.dominio.validador.ValidadorArgumento;

import java.util.List;

public class Usuario {

    private final String usuario;
    private String clave;

    private List<RolUsuario> roles;

    public static Usuario of(String usuario, String clave,List<RolUsuario> roles) {

        ValidadorArgumento.validarObligatorio(usuario, "El usuario no puede ser vacio");
        ValidadorArgumento.validarObligatorio(clave, "La clave no puede ser vacio");
        ValidadorArgumento.validarLongitud(clave, 6L, "La clave debe tener una longitud mínima de %s");
        ValidadorArgumento.validarNoVacia(roles, "Debe tener por lo menos un rol");

        return new Usuario(usuario, clave, roles);
    }

    private Usuario(String usuario, String clave, List<RolUsuario> roles) {
        this.usuario = usuario;
        this.clave = clave;
        this.roles = roles;
    }

    public List<RolUsuario> getRoles() {
        return roles;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public void asignarClaveCifrada(String clave) {
        this.clave = clave;
    }
}
