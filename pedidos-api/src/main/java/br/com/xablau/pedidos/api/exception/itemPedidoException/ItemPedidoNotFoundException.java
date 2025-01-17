package br.com.xablau.pedidos.api.exception.itemPedidoException;

import br.com.xablau.pedidos.api.exception.AbstractExceptionGlobal;
import org.springframework.http.HttpStatus;

public class ItemPedidoNotFoundException extends AbstractExceptionGlobal {

    public ItemPedidoNotFoundException(String message, HttpStatus status, String causeException) {
        super(message, status, causeException);
    }
}
