package com.uco.myproject.infraestructura.adaptador.repositorio;

import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.puerto.RepositorioPersona;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RepositorioPersonaMysql implements RepositorioPersona {

    private final JdbcTemplate jdbcTemplate;

    public RepositorioPersonaMysql(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Persona> listar() {
        String sql = "SELECT nombre, apellido FROM persona";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Persona.of(rs.getString("nombre"), rs.getString("apellido")));
    }

    @Override
    public Persona consultarPorId(Long id) {
        String sql = "SELECT nombre, apellido FROM persona WHERE id = ?";
        List<Persona> personas = jdbcTemplate.query(sql,
                (rs, rowNum) -> Persona.of(rs.getString("nombre"), rs.getString("apellido")),
                id);
        return personas.isEmpty() ? null : personas.get(0);
    }

    @Override
    public Long guardar(Persona persona) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO persona(nombre, apellido) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public boolean existe(Persona persona) {
        String sql = "SELECT COUNT(*) FROM persona WHERE nombre = ? AND apellido = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                persona.getNombre(), persona.getApellido());
        return count != null && count > 0;
    }
}
