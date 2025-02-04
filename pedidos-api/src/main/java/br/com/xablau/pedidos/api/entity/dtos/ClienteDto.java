package br.com.xablau.pedidos.api.entity.dtos;


import br.com.xablau.pedidos.api.entity.Cliente;
import jakarta.validation.constraints.*;

public record ClienteDto (

        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotNull
        @Min(value = 18, message = "The age must be greater than 18 years old.")
        Integer idade,
        @NotBlank
        String endereco,
        @Min(value = 0, message = "the value cannot be negative.")
        @NotNull
        Double saldoAplicativo){


        public ClienteDto(Cliente cliente) {
                this(cliente.getNome(), cliente.getEmail(), cliente.getIdade(), cliente.getEndereco(), cliente.getSaldoAplicativo());
        }
}
