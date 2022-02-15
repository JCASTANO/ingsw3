package com.uco.myproject.infraestructura.adaptador.repositorio;

import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.puerto.RepositorioPersona;
import com.uco.myproject.infraestructura.adaptador.entidad.EntidadPersona;
import com.uco.myproject.infraestructura.adaptador.repositorio.jpa.RepositorioPersonaJpa;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositorioPersonaMysql implements RepositorioPersona {

    private final RepositorioPersonaJpa repositorioPersonaJpa;

    public RepositorioPersonaMysql(RepositorioPersonaJpa repositorioPersonaJpa) {
        this.repositorioPersonaJpa = repositorioPersonaJpa;
    }

    @Override
    public List<Persona> listar() {
        List<EntidadPersona> entidades = this.repositorioPersonaJpa.findAll();
        return entidades.stream().map(entidad -> Persona.of(entidad.getNombre(), entidad.getApellido())).collect(Collectors.toList());
    }

    @Override
    public Persona consultarPorId(Long id) {

        EntidadPersona entidadPersona = this.repositorioPersonaJpa.findById(id).get();

        return Persona.of(entidadPersona.getNombre(), entidadPersona.getApellido());
    }

    @Override
    public Long guardar(Persona persona) {

        EntidadPersona entidadPersona = new EntidadPersona(persona.getNombre(), persona.getApellido());

        return this.repositorioPersonaJpa.save(entidadPersona).getId();
    }

    @Override
    public boolean existe(Persona persona) {
        return this.repositorioPersonaJpa.findByNombreAndApellido(persona.getNombre(), persona.getApellido()) != null;
    }
}
