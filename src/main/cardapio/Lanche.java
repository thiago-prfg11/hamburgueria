package main.cardapio;

import java.util.ArrayList;
import java.util.List;

public class Lanche extends ItemCardapio {

    private final List<Ingrediente> ingredientes;
    private final float preco;

    public Lanche(String descricao, float preco, List<Ingrediente> ingredientes) {
        super(descricao);
        if (preco < 0) {
            throw new IllegalArgumentException("ERR03 - O preço do lanche não pode ser negativo!");
        }
        if (ingredientes == null || ingredientes.isEmpty()) {
            throw new IllegalArgumentException("ERR02 - A lista de ingredientes do lanche não pode ser nula ou vazia!");
        }
        this.preco = preco;
        this.ingredientes = new ArrayList<>(ingredientes);
    }

    public Lanche(ReceitaLanche receita) {
        this(extrairNome(receita), receita.getPrecoBase(), receita.getIngredientesBase());
    }

    private static String extrairNome(ReceitaLanche receita) {
        if (receita == null) {
            throw new IllegalArgumentException("ERR01 - A receita referenciada não pode ser nula!");
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
            total += ingrediente.calorias();
        }
        return total;
    }

    @Override
    public String aceitar(VisitorItemCardapio visitor) {
        return visitor.visitarLanche(this);
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
