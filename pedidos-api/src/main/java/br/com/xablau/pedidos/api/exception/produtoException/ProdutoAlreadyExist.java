package br.com.xablau.pedidos.api.exception.produtoException;

import br.com.xablau.pedidos.api.exception.AbstractExceptionGlobal;
import org.springframework.http.HttpStatus;

public class ProdutoAlreadyExist extends AbstractExceptionGlobal {

    public ProdutoAlreadyExist(String message, HttpStatus status, String causeException) {
        super(message, status, causeException);
    }
}
