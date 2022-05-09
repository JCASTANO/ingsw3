package com.uco.myproject.infraestructura.adaptador.servicio;

import com.uco.myproject.dominio.servicio.ServicioGenerarToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class ServicioGenerarTokenJwt implements ServicioGenerarToken {

    private final Environment environment;

    public ServicioGenerarTokenJwt(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String ejecutar(String usuario, List<String> roles) {

        return Jwts.builder()
                .setIssuer("Universidad Catolica de Oriente")
                .setSubject(usuario)
                .claim("roles", roles)
                .setIssuedAt(createDate(LocalDateTime.now()))
                .setExpiration(createDate(LocalDateTime.now().plusHours(1)))
                .setId(UUID.randomUUID().toString())
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(this.environment.getRequiredProperty("token.key"))
                )
                .compact();
    }

    private static Date createDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
