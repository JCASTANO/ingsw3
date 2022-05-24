package com.uco.myproject.infraestructura.controlador;

import com.uco.myproject.aplicacion.servicio.ServicioAplicacionExportarPersonasExcel;
import com.uco.myproject.aplicacion.servicio.ServicioAplicacionExportarPersonasPdf;
import com.uco.myproject.aplicacion.servicio.ServicioAplicacionGuardarPersona;
import com.uco.myproject.aplicacion.servicio.ServicioAplicacionListarPersonas;
import com.uco.myproject.aplicacion.dto.DtoPersona;
import com.uco.myproject.aplicacion.dto.DtoRespuesta;
import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.infraestructura.aspecto.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class ControladorPersona {

    private final ServicioAplicacionListarPersonas servicioListarPersonas;
    private final ServicioAplicacionGuardarPersona servicioGuardarPersona;
    private final ServicioAplicacionExportarPersonasExcel servicioExportarPersonasExcel;
    private final ServicioAplicacionExportarPersonasPdf servicioExportarPersonasPdf;

    public ControladorPersona(ServicioAplicacionListarPersonas servicioListarPersonas,
                              ServicioAplicacionGuardarPersona servicioGuardarPersona,
                              ServicioAplicacionExportarPersonasExcel servicioAplicacionExportarPersonasExcel,
                              ServicioAplicacionExportarPersonasPdf servicioAplicacionExportarPersonasPdf) {
        this.servicioListarPersonas = servicioListarPersonas;
        this.servicioGuardarPersona = servicioGuardarPersona;
        this.servicioExportarPersonasExcel = servicioAplicacionExportarPersonasExcel;
        this.servicioExportarPersonasPdf = servicioAplicacionExportarPersonasPdf;
    }

    @GetMapping
    @Secured(roles = {"EMPLEADO"})
    public List<Persona> listar() {
        return servicioListarPersonas.ejecutar();
    }

    @GetMapping("/excel")
    @Secured(roles = {"EMPLEADO"})
    public byte[] exportarExcel() {

        final long startTime = System.currentTimeMillis();

        byte[] report = servicioExportarPersonasExcel.ejecutar();

        final long endTime = System.currentTimeMillis();
        final long execution = endTime - startTime;

        System.out.println("tiempo total: " + execution);

        return report;
    }

    @GetMapping("/pdf")
    @Secured(roles = {"EMPLEADO"})
    public byte[] exportarPdf() {
        return servicioExportarPersonasPdf.ejecutar();
    }

    @PostMapping
    @Secured(roles = {"EMPLEADO"})
    public DtoRespuesta<Long> crear(@RequestBody DtoPersona dto) {
        return this.servicioGuardarPersona.ejecutar(dto);
    }
}
