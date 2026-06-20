package main.cardapio;

import java.util.ArrayList;
import java.util.List;

public class Lanche extends ItemCardapio {

    private final List<Ingrediente> ingredientes;
    private final float preco;

    public Lanche(String descricao, float preco, List<Ingrediente> ingredientes) {
        super(descricao);
        if (preco < 0) {
            throw new IllegalArgumentException("O preço do lanche não pode ser negativo!");
        }
        if (ingredientes == null || ingredientes.isEmpty()) {
            throw new IllegalArgumentException("O lanche deve ter ao menos um ingrediente!");
        }
        this.preco = preco;
        this.ingredientes = new ArrayList<>(ingredientes);
    }

    public Lanche(ReceitaLanche receita) {
        this(extrairNome(receita), receita.getPrecoBase(), receita.getIngredientesBase());
    }

    private static String extrairNome(ReceitaLanche receita) {
        if (receita == null) {
            throw new IllegalArgumentException("O nome da receita não pode ser nulo!");
        }
        return receita.getNome();
    }

    public List<Ingrediente> getIngredientes() {
        return new ArrayList<>(this.ingredientes);
    }

    public float getPreco() {
        return this.preco;
    }

    public int getCalorias() {
        int total = 0;
        for (Ingrediente ingrediente : this.ingredientes) {
            total += ingrediente.getCalorias();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Lanche{" +
                "Descrição:'" + getDescricao() + '\'' +
                ", Preço:" + preco +
                ", Quantidade de Calorias:" + getCalorias() +
                '}';
    }
}
