package br.com.xablau.pedidos.api.entity.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDto (
        @NotBlank(message = "Name cannot be blank or null")
        String nome,
        @NotNull(message = "Value cannot be null")
        @Min(value = 0, message = "Value must be greater or equal to 0")
        Double valor) {

}
