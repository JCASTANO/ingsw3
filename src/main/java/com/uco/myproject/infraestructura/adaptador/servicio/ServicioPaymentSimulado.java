package com.uco.myproject.infraestructura.adaptador.servicio;

import com.uco.myproject.dominio.puerto.ServicioPayment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class ServicioPaymentSimulado implements ServicioPayment {

    private final Random random = new Random();

    @Override
    public boolean procesarPago(BigDecimal amount, String paymentMethod) {
        // Simulate payment processing
        // For demonstration, we'll have a 20% chance of payment failure
        return random.nextDouble() > 0.2;
    }
}