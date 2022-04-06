package com.uco.myproject.dominio.servicio;

import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.puerto.RepositorioPersona;
import com.uco.myproject.dominio.puerto.RepositorioTrm;
import org.springframework.stereotype.Service;

@Service
public class ServicioGuardarPersona {

    private static final String MENSAJE_YA_EXISTE = "Ya existe la persona con los datos ingresados";

    private final RepositorioPersona repositorioPersona;
    private final RepositorioTrm repositorioTrm;

    public ServicioGuardarPersona(RepositorioPersona repositorioPersona,RepositorioTrm repositorioTrm) {
        this.repositorioPersona = repositorioPersona;
        this.repositorioTrm = repositorioTrm;
    }

    public Long ejecutar(Persona persona) {

        if(this.repositorioPersona.existe(persona)) {
            throw new IllegalStateException(MENSAJE_YA_EXISTE);
        }

        //this.repositorioTrm.consultarActual();

        return this.repositorioPersona.guardar(persona);
    }
}
