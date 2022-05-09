package com.uco.myproject.infraestructura.aspecto.service;

import com.auth0.jwt.JWT;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class AuthorizationService {

    public boolean isAuthorized(List<String> rolesToAuthorized) {
        List<String> currentRoles = JWT.decode(obtenerTokenActual()).getClaim("roles").asList(String.class);
        return hasRole(rolesToAuthorized, currentRoles);
    }

    private boolean hasRole(List<String> rolesToAuthorized, List<String> currentRoles) {
        boolean result = false;
        for (String role: rolesToAuthorized) {
            if(currentRoles.indexOf(role) != -1) {
                result = true;
            }
        }
        return result;
    }

    private String obtenerTokenActual() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
