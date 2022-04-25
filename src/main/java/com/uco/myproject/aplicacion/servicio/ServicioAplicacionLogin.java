package com.uco.myproject.aplicacion.servicio;

import com.uco.myproject.aplicacion.dto.DtoLogin;
import com.uco.myproject.aplicacion.dto.DtoPersona;
import com.uco.myproject.aplicacion.dto.DtoRespuesta;
import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.servicio.ServicioGenerarToken;
import com.uco.myproject.dominio.servicio.ServicioGuardarPersona;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ServicioAplicacionLogin {

    private final ServicioGenerarToken servicioGenerarToken;

    public ServicioAplicacionLogin(ServicioGenerarToken servicioGenerarToken) {
        this.servicioGenerarToken = servicioGenerarToken;
    }

    public DtoRespuesta<String> ejecutar(DtoLogin dto) {

        //tarea en clase
        //1. consultar si existe un usuario y clave desde la base de datos, sisi retornarle los roles
        //sino lanzar una excepcion y mapearla en el manejador de error
        //pista: crear entidad de usuario y otra para roles

        List<String> roles = Arrays.asList("admin", "empleado");

        String clave = DigestUtils.sha256Hex(dto.getClave());

        System.out.println(clave);

        return new DtoRespuesta<>(this.servicioGenerarToken.ejecutar(dto.getUsuario(), roles));
    }
}
