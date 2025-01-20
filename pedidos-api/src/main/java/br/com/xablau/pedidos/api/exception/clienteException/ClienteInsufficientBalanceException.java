package br.com.xablau.pedidos.api.exception.clienteException;

import br.com.xablau.pedidos.api.exception.AbstractExceptionGlobal;
import org.springframework.http.HttpStatus;

public class ClienteInsufficientBalanceException extends AbstractExceptionGlobal {
    public ClienteInsufficientBalanceException(String message, HttpStatus status, String causeException) {
        super(message, status, causeException);
    }
}
