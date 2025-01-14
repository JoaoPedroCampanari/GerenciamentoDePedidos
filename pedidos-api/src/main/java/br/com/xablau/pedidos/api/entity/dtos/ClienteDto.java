package br.com.xablau.pedidos.api.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    private String nome;
    private String email;
    private Integer idade;
    private String endereco;
    private Double saldoAplicativo;
}
