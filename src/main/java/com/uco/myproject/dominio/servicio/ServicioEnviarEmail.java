package com.uco.myproject.dominio.servicio;

public interface ServicioEnviarEmail {

	void ejecutar(String destinatario,String sujeto, String cuerpo);
}