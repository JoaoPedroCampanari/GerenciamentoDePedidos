package br.com.xablau.pedidos.api.exception.ClienteException;

import br.com.xablau.pedidos.api.exception.AbstractExceptionGlobal;
import org.springframework.http.HttpStatus;

public class ClienteEmailAlreadyExist extends AbstractExceptionGlobal {

    public ClienteEmailAlreadyExist(String message, HttpStatus status, String causeException) {
        super(message, status, causeException);
    }
}
