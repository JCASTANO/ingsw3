package com.uco.myproject.infraestructura.filtro;

import com.uco.myproject.infraestructura.servicio.ServicioValidacionToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FiltroAutenticacion extends OncePerRequestFilter {

    private static final String STRING_EMPTY = "";
    private static final String WILDCARD_ALL = "*";
    private static final String REGEX_ALL = "\\*";
    private static final String MESSAGE_TOKEN_INVALID = "Token does not exist, invalid or expired.";

    private final ServicioValidacionToken servicioValidacionToken;
    private final String[] excludePaths;

    public FiltroAutenticacion(ServicioValidacionToken servicioValidacionToken,String[] excludePaths) {
        this.servicioValidacionToken = servicioValidacionToken;
        this.excludePaths = excludePaths;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!this.servicioValidacionToken.esValido(token)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(),MESSAGE_TOKEN_INVALID);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        boolean shouldNotFilter = false;

        for (int i = 0; i < excludePaths.length; i++) {

            String excludePath = excludePaths[i];

            if(excludePath.endsWith(WILDCARD_ALL)) {
                if(path.startsWith(excludePath.replaceAll(REGEX_ALL, STRING_EMPTY))) {
                    shouldNotFilter = true;
                }
            }else if(excludePath.startsWith(WILDCARD_ALL)) {
                if(path.endsWith(excludePath.replaceAll(REGEX_ALL, STRING_EMPTY))) {
                    shouldNotFilter = true;
                }
            }
            else {
                if(path.equals(excludePath)) {
                    shouldNotFilter = true;
                }
            }
        }

        return shouldNotFilter;
    }
}
