package com.uco.myproject.infraestructura.adaptador.repositorio;

import com.uco.myproject.dominio.dto.DtoTrm;
import com.uco.myproject.dominio.puerto.RepositorioTrm;
import com.uco.myproject.infraestructura.adaptador.exception.TechnicalException;
import com.uco.myproject.infraestructura.adaptador.exception.ToManyRequestException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Repository
public class RepositorioTrmRemoto implements RepositorioTrm {

    private static final Logger logger = LoggerFactory.getLogger(RepositorioTrmRemoto.class);


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
                    this.environment.getRequiredProperty("url.trm"),
                    HttpMethod.GET,
                    entity,
                    DtoTrm.class);

            return response.getBody();

        } catch(HttpStatusCodeException e) {

            logger.error("Error consumiendo el servicio de trm", e);

            if(e.getStatusCode().is4xxClientError()) {
                throw new ToManyRequestException(e);
            } else {
                throw new TechnicalException(e);
            }
        }
    }
}
