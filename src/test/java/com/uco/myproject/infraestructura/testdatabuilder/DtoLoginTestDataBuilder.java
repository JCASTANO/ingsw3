package com.uco.myproject.infraestructura.testdatabuilder;

import com.uco.myproject.aplicacion.dto.DtoLogin;

public class DtoLoginTestDataBuilder {

    private String usuario;
    private String clave;

    public DtoLoginTestDataBuilder() {
        this.usuario = "juan.castano";
        this.clave = "123456";
    }

    public DtoLogin build() {
        return new DtoLogin(usuario, clave);
    }
}
