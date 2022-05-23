package com.uco.myproject.dominio.servicio;

import com.uco.myproject.dominio.modelo.Persona;

import java.util.List;

public interface ServicioExportarPdfPersonas {

    byte[] ejecutar(List<Persona> personas);
}
