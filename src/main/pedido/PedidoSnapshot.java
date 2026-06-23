package main.pedido;

import main.cardapio.ItemCardapio;
import java.util.ArrayList;
import java.util.List;

public record PedidoSnapshot(List<ItemCardapio> itens) {

    public PedidoSnapshot(List<ItemCardapio> itens) {
        if (itens == null) {
            throw new IllegalArgumentException("ERR01 - A lista de itens da snapshot não pode ser nula!");
        }
        this.itens = new ArrayList<>(itens);
    }

    @Override
    public List<ItemCardapio> itens() {
        return new ArrayList<>(this.itens);
    }
}
