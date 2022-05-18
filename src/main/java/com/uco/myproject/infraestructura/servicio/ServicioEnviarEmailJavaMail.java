package com.uco.myproject.infraestructura.servicio;

import com.uco.myproject.dominio.servicio.ServicioEnviarEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ServicioEnviarEmailJavaMail implements ServicioEnviarEmail {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioEnviarEmail.class);

    @Override
    @Async
    public void ejecutar(String destinatario, String sujeto, String cuerpo) {

        LOGGER.info("En clase de email nombre del hilo : " + Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("Email enviado");
    }
}
