package org.example;

public class Pastel {
    private String nome;
    private double preco;

    public Pastel(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
