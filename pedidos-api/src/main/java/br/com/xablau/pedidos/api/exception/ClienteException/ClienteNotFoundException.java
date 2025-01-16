package br.com.xablau.pedidos.api.exception.ClienteException;


public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(String message) {
        super(message);
    }
}
