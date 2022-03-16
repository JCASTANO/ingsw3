package com.uco.myproject.infraestructura.adaptador.exception;

public class ToManyRequestException extends RuntimeException {

    public ToManyRequestException(Exception e) {
        super(e);
    }
}
