package main.cardapio;

import java.util.ArrayList;
import java.util.List;

public class ReceitaLanche implements Cloneable {

    private String nome;
    private List<Ingrediente> ingredientesBase;
    private float precoBase;
    private int tempoPreparoMinutos;

    public ReceitaLanche(String nome, List<Ingrediente> ingredientesBase, float precoBase, int tempoPreparoMinutos) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da receita inserido não pode ser nulo ou em branco!");
        }
        if (ingredientesBase == null) {
            throw new IllegalArgumentException("A lista de ingredientes inserida não pode ser nula!");
        }
        if (precoBase < 0) {
            throw new IllegalArgumentException("O preço base inserido não pode ser negativo!");
        }
        if (tempoPreparoMinutos <= 0) {
            throw new IllegalArgumentException("O tempo de preparo inserido não pode ser negativo!");
        }
        this.nome = nome;
        this.ingredientesBase = ingredientesBase;
        this.precoBase = precoBase;
        this.tempoPreparoMinutos = tempoPreparoMinutos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da receita inserido não pode ser nulo ou em branco!");
        }
        this.nome = nome;
    }

    public List<Ingrediente> getIngredientesBase() {
        return ingredientesBase;
    }

    public float getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(float precoBase) {
        if (precoBase < 0) {
            throw new IllegalArgumentException("O preço base inserido não pode ser negativo!");
        }
        this.precoBase = precoBase;
    }

    public int getTempoPreparoMinutos() {
        return tempoPreparoMinutos;
    }

    public void setTempoPreparoMinutos(int tempoPreparoMinutos) {
        if (tempoPreparoMinutos <= 0) {
            throw new IllegalArgumentException("O tempo de preparo inserido não pode ser negativo!");
        }
        this.tempoPreparoMinutos = tempoPreparoMinutos;
    }

    public void adicionarIngrediente(Ingrediente ingrediente) {
        if (ingrediente == null) {
            throw new IllegalArgumentException("O ingrediente inserido não pode ser nulo!");
        }
        this.ingredientesBase.add(ingrediente);
    }

    public void removerIngrediente(Ingrediente ingrediente) {
        this.ingredientesBase.remove(ingrediente);
    }

    @Override
    public ReceitaLanche clone() throws CloneNotSupportedException {
        ReceitaLanche receitaClone = (ReceitaLanche) super.clone();
        receitaClone.ingredientesBase = new ArrayList<>(this.ingredientesBase);
        return receitaClone;
    }

    @Override
    public String toString() {
        return "ReceitaLanche{" +
                "Nome:'" + nome + '\'' +
                ", Ingredientes Básicos:" + ingredientesBase +
                ", Preço Base:" + precoBase +
                ", Tempo de Preparo:" + tempoPreparoMinutos +
                '}';
    }
}
