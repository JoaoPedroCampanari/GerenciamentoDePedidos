package br.com.xablau.dtos.dtos;

import java.util.Objects;

public class ClienteDto {

    private String nome;
    private String email;
    private Integer idade;
    private String endereco;
    private Double saldoAplicativo;

    public ClienteDto() {
    }

    public ClienteDto(String nome, String email, Integer idade, String endereco, Double saldoAplicativo) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.endereco = endereco;
        this.saldoAplicativo = saldoAplicativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getSaldoAplicativo() {
        return saldoAplicativo;
    }

    public void setSaldoAplicativo(Double saldoAplicativo) {
        this.saldoAplicativo = saldoAplicativo;
    }

    @Override
    public String toString() {
        return "ClienteDto{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", idade=" + idade +
                ", endereco='" + endereco + '\'' +
                ", saldoAplicativo=" + saldoAplicativo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDto that = (ClienteDto) o;
        return Objects.equals(nome, that.nome) && Objects.equals(email, that.email) && Objects.equals(idade, that.idade) && Objects.equals(endereco, that.endereco) && Objects.equals(saldoAplicativo, that.saldoAplicativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, idade, endereco, saldoAplicativo);
    }
}
