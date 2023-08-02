package org.example;

public class ItemPedido {
    private Pastel pastel;
    private int quantidade;

    public ItemPedido(Pastel pastel, int quantidade) {
        this.pastel = pastel;
        this.quantidade = quantidade;
    }

    public Pastel getPastel() {
        return pastel;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double calcularSubtotal() {
        return pastel.getPreco() * quantidade;
    }
}

