package com.uco.myproject.dominio.puerto;

import com.uco.myproject.dominio.modelo.Order;

import java.util.Optional;

public interface RepositorioOrder {
    
    Long guardar(Order order);
    
    Optional<Order> consultarPorId(Long orderId);
    
    boolean existe(Long orderId);
}