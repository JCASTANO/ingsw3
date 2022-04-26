package com.uco.myproject.infraestructura.adaptador.repositorio;

import com.uco.myproject.dominio.modelo.RolUsuario;
import com.uco.myproject.dominio.modelo.Usuario;
import com.uco.myproject.dominio.puerto.RepositorioUsuario;
import com.uco.myproject.infraestructura.adaptador.entidad.EntidadRolUsuario;
import com.uco.myproject.infraestructura.adaptador.entidad.EntidadUsuario;
import com.uco.myproject.infraestructura.adaptador.repositorio.jpa.RepositorioUsuarioJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositorioUsuarioMysql implements RepositorioUsuario {

    private final RepositorioUsuarioJpa repositorioUsuarioJpa;

    public RepositorioUsuarioMysql(RepositorioUsuarioJpa repositorioUsuarioJpa) {
        this.repositorioUsuarioJpa = repositorioUsuarioJpa;
    }

    @Override
    public Long guardar(Usuario usuario) {

        List<EntidadRolUsuario> roles = usuario.getRoles().stream().map(rol -> new EntidadRolUsuario(rol.getRol())).collect(Collectors.toList());
        EntidadUsuario entidadUsuario = new EntidadUsuario(usuario.getUsuario(), usuario.getClave(), roles);

        return this.repositorioUsuarioJpa.save(entidadUsuario).getId();
    }

    @Override
    public boolean existe(Usuario usuario) {
        return this.repositorioUsuarioJpa.findByUsuario(usuario.getUsuario()) != null;
    }

    @Override
    public Usuario consultar(String usuario, String clave) {
        EntidadUsuario entidadUsuario = this.repositorioUsuarioJpa.findByUsuarioAndClave(usuario, clave);

        if(entidadUsuario == null) {
            return null;
        }

        List<RolUsuario> roles = entidadUsuario.getRoles().stream().map(rol -> RolUsuario.of(rol.getRol())).collect(Collectors.toList());
        return Usuario.of(entidadUsuario.getUsuario(), entidadUsuario.getClave(), roles);
    }
}
