package com.uco.myproject.infraestructura.adaptador;

import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.puerto.RepositorioPersona;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Repository
public class RepositorioPersonaEnMemoria implements RepositorioPersona {

    private long secuencia;
    private final HashMap<Long, Persona> personas = new HashMap<>();

    @Override
    public List<Persona> listar() {
        return personas.values().stream().toList();
    }

    @Override
    public Persona consultarPorId(Long id) {
        return personas.get(id);
    }

    @Override
    public Long guardar(Persona persona) {
        personas.put(++secuencia, persona);
        return secuencia;
    }

    @Override
    public boolean existe(Persona persona) {
        return listar().stream().anyMatch(row -> row.toString().equals(persona.toString()));
    }
}
