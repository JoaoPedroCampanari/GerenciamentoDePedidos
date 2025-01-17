package br.com.xablau.dtos;


import java.util.Objects;

public class ProdutoDto {

    private String nome;
    private Double valor;

    public ProdutoDto() {
    }

    public ProdutoDto(String nome, Double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "ProdutoDto{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoDto that = (ProdutoDto) o;
        return Objects.equals(nome, that.nome) && Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, valor);
    }
}
