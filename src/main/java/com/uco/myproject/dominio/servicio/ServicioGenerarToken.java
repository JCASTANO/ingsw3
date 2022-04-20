package com.uco.myproject.dominio.servicio;

import java.util.List;

public interface ServicioGenerarToken {

    String ejecutar(String usuario, List<String> roles);
}
