package com.uco.myproject.infraestructura.adaptador.servicio;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.uco.myproject.dominio.dto.DtoUsuarioActual;
import com.uco.myproject.dominio.servicio.ServicioObtenerUsuarioActual;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class ServicioObtenerUsuarioActualJwt implements ServicioObtenerUsuarioActual {

    @Override
    public DtoUsuarioActual ejecutar() {
        DecodedJWT decoded = JWT.decode(obtenerTokenActual());
        return new DtoUsuarioActual(decoded.getSubject(), decoded.getClaim("roles").asList(String.class));
    }

    private String obtenerTokenActual() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
