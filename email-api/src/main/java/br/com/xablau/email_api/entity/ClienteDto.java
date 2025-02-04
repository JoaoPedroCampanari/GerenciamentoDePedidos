package br.com.xablau.email_api.entity;

public record ClienteDto (
        String nome,
        String email,
        Integer idade,
        String endereco,
        Double saldoAplicativo){

}