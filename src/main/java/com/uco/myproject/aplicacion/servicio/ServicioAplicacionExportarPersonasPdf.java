package com.uco.myproject.aplicacion.servicio;

import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.puerto.RepositorioPersona;
import com.uco.myproject.dominio.servicio.ServicioExportarPdfPersonas;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicioAplicacionExportarPersonasPdf {

    private final RepositorioPersona repositorioPersona;
    private final ServicioExportarPdfPersonas servicioExportarPdfPersonas;

    public ServicioAplicacionExportarPersonasPdf(RepositorioPersona repositorioPersona, ServicioExportarPdfPersonas servicioExportarPdfPersonas) {
        this.repositorioPersona = repositorioPersona;
        this.servicioExportarPdfPersonas = servicioExportarPdfPersonas;
    }

    public byte[] ejecutar() {

        List<Persona> personas = this.repositorioPersona.listar();

        return this.servicioExportarPdfPersonas.ejecutar(personas);
    }
}
