package com.uco.myproject.infraestructura.error;

import java.util.concurrent.ConcurrentHashMap;

import com.uco.myproject.infraestructura.aspecto.exception.ExceptionUserUnauthorized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ManejadorError extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger(ManejadorError.class);

    private static final String OCURRIO_UN_ERROR_FAVOR_CONTACTAR_AL_ADMINISTRADOR = "Ocurrió un error favor contactar al administrador.";

    private static final ConcurrentHashMap<String, Integer> CODIGOS_ESTADO = new ConcurrentHashMap<>();

    public ManejadorError() {
        CODIGOS_ESTADO.put(IllegalStateException.class.getSimpleName(), HttpStatus.CONFLICT.value());
        CODIGOS_ESTADO.put(ExceptionUserUnauthorized.class.getSimpleName(), HttpStatus.FORBIDDEN.value());
        // Order management exceptions
        CODIGOS_ESTADO.put(IllegalArgumentException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put("PaymentFailedException", HttpStatus.PAYMENT_REQUIRED.value());
        CODIGOS_ESTADO.put("InsufficientStockException", HttpStatus.CONFLICT.value());
        CODIGOS_ESTADO.put("StockReservationException", HttpStatus.CONFLICT.value());
        CODIGOS_ESTADO.put("ProductNotFoundException", HttpStatus.CONFLICT.value());
        //en caso de tener otra excepcion propia matricularla aca
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Error> handleAllExceptions(Exception exception) {
        ResponseEntity<Error> resultado;

        String excepcionNombre = exception.getClass().getSimpleName();
        String mensaje = exception.getMessage();
        Integer codigo = CODIGOS_ESTADO.get(excepcionNombre);

        if (codigo != null) {
            Error error = new Error(excepcionNombre, mensaje);
            resultado = new ResponseEntity<>(error, HttpStatus.valueOf(codigo));
        } else {
            LOGGER_ERROR.error(excepcionNombre, exception);
            Error error = new Error(excepcionNombre, OCURRIO_UN_ERROR_FAVOR_CONTACTAR_AL_ADMINISTRADOR);
            resultado = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return resultado;
    }



}
