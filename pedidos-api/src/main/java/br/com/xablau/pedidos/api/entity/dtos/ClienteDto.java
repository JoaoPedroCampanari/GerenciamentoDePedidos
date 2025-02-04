package br.com.xablau.pedidos.api.entity.dtos;


import br.com.xablau.pedidos.api.entity.Cliente;
import jakarta.validation.constraints.*;

public record ClienteDto (

        @NotBlank(message = "The name cannot be blank or null")
        String nome,
        @NotBlank(message = "The email cannot be blank or null")
        @Email(message = "The email must have @ and .")
        String email,
        @NotNull(message = "The age cannot be null")
        @Min(value = 18, message = "The age must be greater than 18 years old.")
        Integer idade,
        @NotBlank(message = "The address cannot be blank or null")
        String endereco,
        @Min(value = 0, message = "the value cannot be negative.")
        @NotNull(message = "The balance cannot be null")
        Double saldoAplicativo){


        public ClienteDto(Cliente cliente) {
                this(cliente.getNome(), cliente.getEmail(), cliente.getIdade(), cliente.getEndereco(), cliente.getSaldoAplicativo());
        }
}
