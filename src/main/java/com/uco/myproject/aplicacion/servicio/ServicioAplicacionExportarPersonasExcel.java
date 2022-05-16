package com.uco.myproject.aplicacion.servicio;

import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.puerto.RepositorioPersona;
import com.uco.myproject.dominio.servicio.ServicioEnviarEmail;
import com.uco.myproject.dominio.servicio.ServicioExportarExcel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicioAplicacionExportarPersonasExcel {

    private final RepositorioPersona repositorioPersona;
    private final ServicioExportarExcel servicioExportarExcel;
    private final ServicioEnviarEmail servicioEnviarEmail;

    public ServicioAplicacionExportarPersonasExcel(RepositorioPersona repositorioPersona,ServicioExportarExcel servicioExportarExcel,
                                                   ServicioEnviarEmail servicioEnviarEmail) {
        this.repositorioPersona = repositorioPersona;
        this.servicioExportarExcel = servicioExportarExcel;
        this.servicioEnviarEmail = servicioEnviarEmail;
    }

    public byte[] ejecutar() {

        List<Persona> personas = this.repositorioPersona.listar();

        this.servicioEnviarEmail.ejecutar("uncorreo@gmail.com","Reporte Generado", "Se ha generado un reporte en el sistema");

        return this.servicioExportarExcel.ejecutar(personas, "personas.xls");
    }
}
