package br.com.xablau.pedidos.api.exception.ClienteException;


import br.com.xablau.pedidos.api.exception.AbstractExceptionGlobal;
import org.springframework.http.HttpStatus;

public class ClienteNotFoundException extends AbstractExceptionGlobal {

    public ClienteNotFoundException(String message, HttpStatus status, String causeException) {
        super(message, status, causeException);
    }
}
