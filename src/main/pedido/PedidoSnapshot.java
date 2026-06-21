package main.pedido;

import main.cardapio.ItemCardapio;

import java.util.ArrayList;
import java.util.List;

public class PedidoSnapshot {

    private final List<ItemCardapio> itens;

    public PedidoSnapshot(List<ItemCardapio> itens) {
        if (itens == null) {
            throw new IllegalArgumentException("A lista de itens da snapshot não pode ser nula!");
        }
        this.itens = new ArrayList<>(itens);
    }

    public List<ItemCardapio> getItens() {
        return new ArrayList<>(this.itens);
    }
}
