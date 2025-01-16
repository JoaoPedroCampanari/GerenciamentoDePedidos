package br.com.xablau.pedidos.api.exception;


import org.springframework.http.HttpStatus;

public class AbstractExceptionGlobal extends RuntimeException{

    private HttpStatus status;
    private String causeException;

    public AbstractExceptionGlobal(String message, HttpStatus status, String causeException) {
        super(message);
        this.status = status;
        this.causeException = causeException;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCauseException() {
        return causeException;
    }
}
