package br.com.xablau.pedidos.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractExceptionGlobal.class)
    private ResponseEntity<Map<String,Object>> exceptionHandler(AbstractExceptionGlobal ex){
        Map<String, Object> mensagem = new HashMap<>();

        mensagem.put("status", ex.getStatus().value());
        mensagem.put("error", ex.getCauseException());
        mensagem.put("message", ex.getMessage());
        mensagem.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(ex.getStatus()).body(mensagem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex){
        var errors = ex.getFieldErrors();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.stream().map(DadosErroValidacao::new).toList());
    }

    private record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
