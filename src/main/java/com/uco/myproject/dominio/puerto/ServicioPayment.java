package com.uco.myproject.dominio.puerto;

import java.math.BigDecimal;

public interface ServicioPayment {
    
    boolean procesarPago(BigDecimal amount, String paymentMethod);
}