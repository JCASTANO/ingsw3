package com.uco.myproject.infraestructura.controlador;

import com.uco.myproject.aplicacion.dto.DtoRespuesta;
import com.uco.myproject.aplicacion.dto.DtoUsuario;
import com.uco.myproject.aplicacion.servicio.ServicioAplicacionRegistrarUsuario;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/usuarios")
public class ControladorUsuario {

    private final ServicioAplicacionRegistrarUsuario servicioAplicacionRegistrarUsuario;

    public ControladorUsuario(ServicioAplicacionRegistrarUsuario servicioAplicacionRegistrarUsuario) {
        this.servicioAplicacionRegistrarUsuario = servicioAplicacionRegistrarUsuario;
    }

    @PostMapping
    public DtoRespuesta<Long> crear(@RequestBody DtoUsuario dto) {
        return this.servicioAplicacionRegistrarUsuario.ejecutar(dto);
    }
}
