package atividade_lp2.model;

import java.math.BigDecimal;

public class Produto {
    private String id;
    private String nomeProduto;
    private BigDecimal preco;
    private int quantidade;
    private String avaliacao;

    public Produto() {}

    public Produto(String nomeProduto, BigDecimal preco, int quantidade, String avaliacao) {
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.avaliacao = avaliacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", nomeProduto=" + nomeProduto + ", preco=" + preco
                + ", quantidade=" + quantidade + ", avaliacao=" + avaliacao + '}';
    }
}
