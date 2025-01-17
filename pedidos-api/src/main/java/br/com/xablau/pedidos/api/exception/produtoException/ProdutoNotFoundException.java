package br.com.xablau.pedidos.api.exception.produtoException;

import br.com.xablau.pedidos.api.exception.AbstractExceptionGlobal;
import org.springframework.http.HttpStatus;

public class ProdutoNotFoundException extends AbstractExceptionGlobal {
    public ProdutoNotFoundException(String message, HttpStatus status, String causeException) {
        super(message, status, causeException);
    }
}
