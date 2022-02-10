package com.example.demo.infraestructura.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorPersona {

    @GetMapping("/personas")
    public String listar() {
        return "hola";
    }

    @PostMapping("/personas")
    public void crear() {

    }
}
