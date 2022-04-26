package com.uco.myproject.aplicacion.servicio;

import com.uco.myproject.aplicacion.dto.DtoRespuesta;
import com.uco.myproject.aplicacion.dto.DtoUsuario;
import com.uco.myproject.dominio.modelo.RolUsuario;
import com.uco.myproject.dominio.modelo.Usuario;
import com.uco.myproject.dominio.servicio.ServicioCifrarTexto;
import com.uco.myproject.dominio.servicio.ServicioGuardarUsuario;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ServicioAplicacionRegistrarUsuario {

    private final ServicioGuardarUsuario servicioGuardarUsuario;
    private final ServicioCifrarTexto servicioCifrarTexto;

    public ServicioAplicacionRegistrarUsuario(ServicioGuardarUsuario servicioGuardarUsuario,ServicioCifrarTexto servicioCifrarTexto) {
        this.servicioGuardarUsuario = servicioGuardarUsuario;
        this.servicioCifrarTexto = servicioCifrarTexto;
    }

    public DtoRespuesta<Long> ejecutar(DtoUsuario dto) {

        List<RolUsuario> roles = Arrays.asList(RolUsuario.of("EMPLEADO"),RolUsuario.of("EGRESADO"));

        Usuario usuario = Usuario.of(dto.getUsuario(), dto.getClave(), roles);

        String claveCifrada = this.servicioCifrarTexto.ejecutar(usuario.getClave());
        usuario.asignarClaveCifrada(claveCifrada);

        return new DtoRespuesta<>(this.servicioGuardarUsuario.ejecutar(usuario));
    }
}
