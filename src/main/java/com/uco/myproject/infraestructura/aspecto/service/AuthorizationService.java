package com.uco.myproject.infraestructura.aspecto.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class AuthorizationService {

    private final Environment environment;

    public AuthorizationService(Environment environment) {
        this.environment = environment;
    }

    public boolean isAuthorized(String roleToAuthorized) {
        String token = obtenerTokenActual();
        DecodedJWT decoded = JWT.decode(obtenerTokenActual());
        List<String> roles = decoded.getClaim("roles").asList(String.class);
        return roles.indexOf(roleToAuthorized) != -1;
    }

    private String obtenerTokenActual() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
