package com.uco.myproject.infraestructura.adaptador.repositorio.jpa;

import com.uco.myproject.infraestructura.adaptador.entidad.EntidadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuarioJpa extends JpaRepository<EntidadUsuario, Long> {

    EntidadUsuario findByUsuario(String usuario);
    EntidadUsuario findByUsuarioAndClave(String usuario,String clave);
}
