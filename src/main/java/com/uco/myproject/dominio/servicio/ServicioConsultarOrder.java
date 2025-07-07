package com.uco.myproject.dominio.servicio;

import com.uco.myproject.dominio.modelo.Order;
import com.uco.myproject.dominio.puerto.RepositorioOrder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioConsultarOrder {

    private final RepositorioOrder repositorioOrder;

    public ServicioConsultarOrder(RepositorioOrder repositorioOrder) {
        this.repositorioOrder = repositorioOrder;
    }

    public Optional<Order> ejecutar(Long orderId) {
        return repositorioOrder.consultarPorId(orderId);
    }
}