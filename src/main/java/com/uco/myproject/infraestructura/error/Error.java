package com.uco.myproject.infraestructura.error;

public class Error {

    private final String nombreExcepcion;
    private final String mensaje;

    public Error(String nombreExcepcion, String mensaje) {
        this.nombreExcepcion = nombreExcepcion;
        this.mensaje = mensaje;
    }

    public String getNombreExcepcion() {
        return nombreExcepcion;
    }

    public String getMensaje() {
        return mensaje;
    }
}
