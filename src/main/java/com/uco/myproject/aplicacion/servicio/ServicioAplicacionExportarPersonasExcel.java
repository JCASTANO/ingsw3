package com.uco.myproject.aplicacion.servicio;

import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.puerto.RepositorioPersona;
import com.uco.myproject.dominio.servicio.ServicioExportarExcel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicioAplicacionExportarPersonasExcel {

    private final RepositorioPersona repositorioPersona;
    private final ServicioExportarExcel servicioExportarExcel;

    public ServicioAplicacionExportarPersonasExcel(RepositorioPersona repositorioPersona,ServicioExportarExcel servicioExportarExcel) {
        this.repositorioPersona = repositorioPersona;
        this.servicioExportarExcel = servicioExportarExcel;
    }

    public byte[] ejecutar() {

        List<Persona> personas = this.repositorioPersona.listar();

        return this.servicioExportarExcel.ejecutar(personas, "personas.xls");
    }
}
