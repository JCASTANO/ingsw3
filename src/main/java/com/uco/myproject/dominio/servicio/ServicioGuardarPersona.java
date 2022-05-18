package com.uco.myproject.dominio.servicio;

import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.puerto.RepositorioPersona;
import org.springframework.stereotype.Service;

@Service
public class ServicioGuardarPersona {

    private static final String MENSAJE_YA_EXISTE = "Ya existe la persona con los datos ingresados";

    private final RepositorioPersona repositorioPersona;

    public ServicioGuardarPersona(RepositorioPersona repositorioPersona) {
        this.repositorioPersona = repositorioPersona;
    }

    public Long ejecutar(Persona persona) {

        if(this.repositorioPersona.existe(persona)) {
            throw new IllegalStateException(MENSAJE_YA_EXISTE);
        }

        if(PalindromeChecker.validate(persona.getNombre())) {
            //https://martinfowler.com/bliki/TellDontAsk.html
            System.out.println("Ejecutar proceso de negocio con palindromo sin aplicar tell dont ask");
        }

        if(persona.tieneNombrePalindromo()) {
            //https://martinfowler.com/bliki/TellDontAsk.html
            System.out.println("Ejecutar proceso de negocio con palindromo aplicando tell dont ask");
        }

        return this.repositorioPersona.guardar(persona);
    }
}
