package main.cardapio;

import java.util.ArrayList;
import java.util.List;

public class Combo extends ItemCardapio {

    private final List<ItemCardapio> itens;
    private final float descontoPercentual;

    public Combo(String descricao, float descontoPercentual) {
        super(descricao);
        if (descontoPercentual < 0 || descontoPercentual > 100) {
            throw new IllegalArgumentException("A quantidade de desconto do combo é inválida!");
        }
        this.descontoPercentual = descontoPercentual;
        this.itens = new ArrayList<>();
    }

    public void addItem(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("O item inserido não pode ser nulo!");
        }
        if (item == this) {
            throw new IllegalArgumentException("Um combo não pode conter a si mesmo!");
        }
        this.itens.add(item);
    }

    public void removerItem(ItemCardapio item) {
        this.itens.remove(item);
    }

    public List<ItemCardapio> getItens() {
        return new ArrayList<>(this.itens);
    }

    public float getDescontoPercentual() {
        return descontoPercentual;
    }

    public float getPreco() {
        if (this.itens.isEmpty()) {
            throw new IllegalStateException("Não é possível calcular o preço de um combo sem itens!");
        }
        float total = 0;
        for (ItemCardapio item : this.itens) {
            total += item.getPreco();
        }
        return total * (1 - (this.descontoPercentual / 100));
    }

    public int getCalorias() {
        int total = 0;
        for (ItemCardapio item : this.itens) {
            total += item.getCalorias();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Combo{" +
                "Descrição:'" + getDescricao() + '\'' +
                ", Itens:" + itens.size() +
                ", Desconto:" + descontoPercentual +
                '}';
    }
}
