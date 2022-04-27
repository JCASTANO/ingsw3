package com.uco.myproject.infraestructura.controlador;

import com.uco.myproject.aplicacion.servicio.ServicioAplicacionGuardarPersona;
import com.uco.myproject.aplicacion.servicio.ServicioAplicacionListarPersonas;
import com.uco.myproject.aplicacion.dto.DtoPersona;
import com.uco.myproject.aplicacion.dto.DtoRespuesta;
import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.infraestructura.aspecto.SecuredResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/personas")
public class ControladorPersona {

    private final ServicioAplicacionListarPersonas servicioListarPersonas;
    private final ServicioAplicacionGuardarPersona servicioGuardarPersona;

    public ControladorPersona(ServicioAplicacionListarPersonas servicioListarPersonas, ServicioAplicacionGuardarPersona servicioGuardarPersona) {
        this.servicioListarPersonas = servicioListarPersonas;
        this.servicioGuardarPersona = servicioGuardarPersona;
    }

    @GetMapping
    @SecuredResource(name = "EMPLEADO")
    public List<Persona> listar() {
        return servicioListarPersonas.ejecutar();
    }

    @PostMapping
    @SecuredResource(name = "EMPLEADO")
    public DtoRespuesta<Long> crear(@RequestBody DtoPersona dto) {
        return this.servicioGuardarPersona.ejecutar(dto);
    }
}
