package com.uco.myproject.infraestructura.adaptador.repositorio;

import com.uco.myproject.dominio.modelo.RolUsuario;
import com.uco.myproject.dominio.modelo.Usuario;
import com.uco.myproject.dominio.puerto.RepositorioUsuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RepositorioUsuarioMysql implements RepositorioUsuario {

    private final JdbcTemplate jdbcTemplate;

    public RepositorioUsuarioMysql(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long guardar(Usuario usuario) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO usuario(usuario, clave) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getClave());
            return ps;
        }, keyHolder);

        Long userId = keyHolder.getKey().longValue();
        for (RolUsuario rol : usuario.getRoles()) {
            jdbcTemplate.update(
                    "INSERT INTO rol_usuario(rol, id_usuario) VALUES (?, ?)",
                    rol.getRol(), userId);
        }
        return userId;
    }

    @Override
    public boolean existe(Usuario usuario) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM usuario WHERE usuario = ?",
                Integer.class,
                usuario.getUsuario());
        return count != null && count > 0;
    }

    @Override
    public Usuario consultar(String usuario, String clave) {
        String sql = "SELECT id, usuario, clave FROM usuario WHERE usuario = ? AND clave = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            List<RolUsuario> roles = jdbcTemplate.query(
                    "SELECT rol FROM rol_usuario WHERE id_usuario = ?",
                    (rsRol, num) -> RolUsuario.of(rsRol.getString("rol")),
                    id);
            return Usuario.of(rs.getString("usuario"), rs.getString("clave"), roles);
        }, usuario, clave);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }
}
