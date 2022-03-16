package com.uco.myproject.infraestructura.adaptador.repositorio;

import com.uco.myproject.dominio.dto.DtoTrm;
import com.uco.myproject.dominio.puerto.RepositorioTrm;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Repository
public class RepositorioTrmRemoto implements RepositorioTrm {

    private final RestTemplate restTemplate;
    private final Environment environment;

    public RepositorioTrmRemoto(RestTemplate restTemplate,Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    @Override
    @Retryable( value = RuntimeException.class,
            maxAttempts = 3, backoff = @Backoff(delay = 7000))
    public DtoTrm consultarActual() {

        try {

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<DtoTrm> response = this.restTemplate.exchange(
                    "https://sw3-trm.herokuapp.com/trm",
                    HttpMethod.GET,
                    entity,
                    DtoTrm.class);

            return response.getBody();

        } catch(HttpStatusCodeException e) {
            throw new RuntimeException(e);
        }
    }
}
