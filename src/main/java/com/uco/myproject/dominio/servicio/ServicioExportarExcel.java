package com.uco.myproject.dominio.servicio;

import java.util.List;

public interface ServicioExportarExcel {

	byte[] ejecutar(List<? extends Object> registros, String template);
}