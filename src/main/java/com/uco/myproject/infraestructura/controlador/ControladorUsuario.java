package com.uco.myproject.infraestructura.controlador;

import com.uco.myproject.aplicacion.dto.DtoRespuesta;
import com.uco.myproject.aplicacion.dto.DtoUsuario;
import com.uco.myproject.aplicacion.servicio.ServicioAplicacionRegistrarUsuario;
import com.uco.myproject.dominio.dto.DtoUsuarioActual;
import com.uco.myproject.dominio.servicio.ServicioObtenerUsuarioActual;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/usuarios")
public class ControladorUsuario {

    private final ServicioAplicacionRegistrarUsuario servicioAplicacionRegistrarUsuario;
    private final ServicioObtenerUsuarioActual servicioObtenerUsuarioActual;

    public ControladorUsuario(ServicioAplicacionRegistrarUsuario servicioAplicacionRegistrarUsuario,ServicioObtenerUsuarioActual servicioObtenerUsuarioActual) {
        this.servicioAplicacionRegistrarUsuario = servicioAplicacionRegistrarUsuario;
        this.servicioObtenerUsuarioActual = servicioObtenerUsuarioActual;
    }

    @GetMapping("/actual")
    public DtoUsuarioActual obtenerActual() {
        return this.servicioObtenerUsuarioActual.ejecutar();
    }

    @PostMapping
    public DtoRespuesta<Long> crear(@RequestBody DtoUsuario dto) {
        return this.servicioAplicacionRegistrarUsuario.ejecutar(dto);
    }
}
