package com.uco.myproject.infraestructura.controlador;

import com.uco.myproject.aplicacion.dto.DtoOrderRequest;
import com.uco.myproject.aplicacion.dto.DtoOrderResponse;
import com.uco.myproject.aplicacion.dto.DtoRespuesta;
import com.uco.myproject.aplicacion.servicio.ServicioAplicacionConsultarOrder;
import com.uco.myproject.aplicacion.servicio.ServicioAplicacionCrearOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class ControladorOrder {

    private final ServicioAplicacionCrearOrder servicioCrearOrder;
    private final ServicioAplicacionConsultarOrder servicioConsultarOrder;

    public ControladorOrder(ServicioAplicacionCrearOrder servicioCrearOrder, 
                           ServicioAplicacionConsultarOrder servicioConsultarOrder) {
        this.servicioCrearOrder = servicioCrearOrder;
        this.servicioConsultarOrder = servicioConsultarOrder;
    }

    @PostMapping
    public ResponseEntity<DtoOrderResponse> crear(@RequestBody DtoOrderRequest request) {
        DtoRespuesta<DtoOrderResponse> response = servicioCrearOrder.ejecutar(request);
        return new ResponseEntity<>(response.getValor(), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> consultar(@PathVariable Long orderId) {
        Optional<DtoOrderResponse> order = servicioConsultarOrder.ejecutar(orderId);
        
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Orden no encontrada"));
        }
    }
}