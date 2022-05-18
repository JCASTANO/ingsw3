package com.uco.myproject.infraestructura;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TareaProgramadaEnviarCorreo {

    //https://riptutorial.com/spring/example/21209/cron-expression

    //cada 60 segundos
    @Scheduled(cron = "*/60 * * * * *")
    public void scheduleTaskUsingCronExpression() {

        System.out.println("Enviando correo desde tarea programada " + LocalDateTime.now());
    }

    //todos los días a las 8pm
    @Scheduled(cron = "0 0 20 * * ?")
    public void scheduleTaskAtNight() {

        System.out.println("Enviando correo desde tarea programada en la noche " + LocalDateTime.now());
    }
}
