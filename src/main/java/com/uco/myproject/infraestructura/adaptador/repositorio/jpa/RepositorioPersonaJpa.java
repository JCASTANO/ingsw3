package com.uco.myproject.infraestructura.adaptador.repositorio.jpa;

import com.uco.myproject.infraestructura.adaptador.entidad.EntidadPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPersonaJpa extends JpaRepository<EntidadPersona, Long> {

    EntidadPersona findByNombreAndApellido(String nombre, String apellido);
}
