package main.cardapio;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Cardapio implements Iterable<ItemCardapio> {

    private final List<ItemCardapio> itens;

    public Cardapio(ItemCardapio... itens) {
        if (itens == null) {
            throw new IllegalArgumentException("ERR01 - A lista de itens referenciada não pode ser nula!");
        }
        this.itens = Arrays.asList(itens);
    }

    @Override
    public Iterator<ItemCardapio> iterator() {
        return itens.iterator();
    }
}
