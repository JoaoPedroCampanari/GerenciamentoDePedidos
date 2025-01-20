package br.com.xablau.pedidos.api.exception.pedidoException;

import br.com.xablau.pedidos.api.exception.AbstractExceptionGlobal;
import org.springframework.http.HttpStatus;

public class PedidoNotFoundException extends AbstractExceptionGlobal {

    public PedidoNotFoundException(String message, HttpStatus status, String causeException) {
        super(message, status, causeException);
    }
}
