package br.com.xablau.pedidos.api.exception;


import br.com.xablau.pedidos.api.exception.ClienteException.ClienteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    private ResponseEntity<Map<String,Object>> exceptionHandler(ClienteNotFoundException ex){
        Map<String, Object> body = new HashMap<>();

        body.put("status", "404");
        body.put("error", "NOT FOUND");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
