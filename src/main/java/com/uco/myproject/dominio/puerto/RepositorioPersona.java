package com.uco.myproject.dominio.puerto;

import com.uco.myproject.dominio.modelo.Persona;

import java.util.List;

public interface RepositorioPersona {

    List<Persona> listar();
    Persona consultarPorId(Long id);
    Long guardar(Persona persona);
    boolean existe(Persona persona);
}
