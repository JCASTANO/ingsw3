package com.uco.myproject.infraestructura.adaptador.entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class EntidadUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String usuario;
    private String clave;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name= "id_usuario")
    private List<EntidadRolUsuario> roles;

    public EntidadUsuario() {}

    public EntidadUsuario(String usuario, String clave,List<EntidadRolUsuario> roles) {
        this.usuario  = usuario;
        this.clave = clave;
        this.roles = roles;
    }

    public List<EntidadRolUsuario> getRoles() {
        return roles;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public Long getId() {
        return id;
    }
}
