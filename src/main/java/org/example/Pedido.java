package org.example;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Cliente cliente;
    private boolean encerrado;
    private List<ItemPedido> itens;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.encerrado = false;
        this.itens = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void encerrarPedido() {
        encerrado = true;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }
}
