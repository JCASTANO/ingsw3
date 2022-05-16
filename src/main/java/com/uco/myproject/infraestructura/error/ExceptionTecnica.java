package com.uco.myproject.infraestructura.error;

public class ExceptionTecnica extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public ExceptionTecnica(String mensaje, Exception e) {
        super(mensaje, e);
    }
    
    public ExceptionTecnica(String mensaje) {
        super(mensaje);
    }
}
