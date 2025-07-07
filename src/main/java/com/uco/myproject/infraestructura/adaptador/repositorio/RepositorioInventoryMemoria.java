package com.uco.myproject.infraestructura.adaptador.repositorio;

import com.uco.myproject.dominio.modelo.Inventory;
import com.uco.myproject.dominio.puerto.RepositorioInventory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RepositorioInventoryMemoria implements RepositorioInventory {

    private final Map<Long, Inventory> inventory = new ConcurrentHashMap<>();

    public RepositorioInventoryMemoria() {
        // Initialize with test data as specified in the requirements
        inventory.put(456L, Inventory.of(456L, 5, new BigDecimal("100.00")));
        inventory.put(789L, Inventory.of(789L, 2, new BigDecimal("250.00")));
        inventory.put(321L, Inventory.of(321L, 10, new BigDecimal("50.00")));
    }

    @Override
    public Optional<Inventory> consultarPorProductId(Long productId) {
        return Optional.ofNullable(inventory.get(productId));
    }

    @Override
    public void actualizarStock(Inventory inventoryItem) {
        inventory.put(inventoryItem.getProductId(), inventoryItem);
    }

    @Override
    public boolean existeProducto(Long productId) {
        return inventory.containsKey(productId);
    }
}