package com.uco.myproject.dominio.puerto;

import com.uco.myproject.dominio.modelo.Usuario;

public interface RepositorioUsuario {

    Long guardar(Usuario usuario);
    boolean existe(Usuario usuario);
    Usuario consultar(String usuario, String clave);
}
