package com.uco.myproject.infraestructura.adaptador.repositorio;

import com.uco.myproject.dominio.modelo.Order;
import com.uco.myproject.dominio.puerto.RepositorioOrder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RepositorioOrderMemoria implements RepositorioOrder {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Long guardar(Order order) {
        Long orderId = idGenerator.getAndIncrement();
        Order orderWithId = order.withId(orderId);
        orders.put(orderId, orderWithId);
        return orderId;
    }

    @Override
    public Optional<Order> consultarPorId(Long orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    @Override
    public boolean existe(Long orderId) {
        return orders.containsKey(orderId);
    }
}