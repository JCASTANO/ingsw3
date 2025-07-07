package com.uco.myproject.dominio.puerto;

import com.uco.myproject.dominio.modelo.Inventory;

import java.util.Optional;

public interface RepositorioInventory {
    
    Optional<Inventory> consultarPorProductId(Long productId);
    
    void actualizarStock(Inventory inventory);
    
    boolean existeProducto(Long productId);
}