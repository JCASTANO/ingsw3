package com.uco.myproject.infraestructura.aspecto.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
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
        final SigningKeyResolver firma = obtenerFirma();
        List<String> roles = obtenerRoles(token, firma);
        return roles.indexOf(roleToAuthorized) != -1;
    }

    private List<String> obtenerRoles(String token, SigningKeyResolver signingKeyResolver) {
        return (List<String>) Jwts.parser()
                .setSigningKeyResolver(signingKeyResolver)
                .parseClaimsJws(token).getBody().get("roles");
    }

    private SigningKeyResolver obtenerFirma() {
        final SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
            @Override
            public byte[] resolveSigningKeyBytes(@SuppressWarnings("rawtypes") JwsHeader header, Claims claims) {
                return TextCodec.BASE64.decode(AuthorizationService.this.environment.getRequiredProperty("token.key"));
            }
        };
        return signingKeyResolver;
    }

    private String obtenerTokenActual() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
