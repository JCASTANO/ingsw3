package com.uco.myproject.infraestructura.adaptador.exception;

public class TechnicalException extends RuntimeException {

    public TechnicalException(Exception e) {
        super(e);
    }
}
