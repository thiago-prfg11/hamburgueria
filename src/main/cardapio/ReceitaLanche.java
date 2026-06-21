package main.cardapio;

import java.util.ArrayList;
import java.util.List;

public class ReceitaLanche implements Cloneable {

    private String nome;
    private List<Ingrediente> ingredientesBase;
    private float precoBase;
    private int tempoPreparoMinutos;
    private final TecnicaPreparo tecnicaPreparo;

    public ReceitaLanche(String nome, List<Ingrediente> ingredientesBase, float precoBase, int tempoPreparoMinutos, TecnicaPreparo tecnicaPreparo) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da receita não pode ser nulo ou em branco!");
        }
        if (ingredientesBase == null) {
            throw new IllegalArgumentException("A lista de ingredientes não pode ser nula!");
        }
        if (precoBase < 0) {
            throw new IllegalArgumentException("O preço base não pode ser negativo!");
        }
        if (tempoPreparoMinutos <= 0) {
            throw new IllegalArgumentException("O tempo de preparo deve ser, no mínimo, de 1 minuto!");
        }
        if (tecnicaPreparo == null) {
            throw new IllegalArgumentException("A técnica de preparo não pode ser nula!");
        }
        this.nome = nome;
        this.ingredientesBase = new ArrayList<Ingrediente>(ingredientesBase);
        this.precoBase = precoBase;
        this.tempoPreparoMinutos = tempoPreparoMinutos;
        this.tecnicaPreparo = tecnicaPreparo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da receita não pode ser nulo ou em branco!");
        }
        this.nome = nome;
    }

    public List<Ingrediente> getIngredientesBase() {
        return new ArrayList<Ingrediente>(this.ingredientesBase);
    }

    public float getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(float precoBase) {
        if (precoBase < 0) {
            throw new IllegalArgumentException("O preço base não pode ser negativo!");
        }
        this.precoBase = precoBase;
    }

    public int getTempoPreparoMinutos() {
        return tempoPreparoMinutos;
    }

    public void setTempoPreparoMinutos(int tempoPreparoMinutos) {
        if (tempoPreparoMinutos <= 0) {
            throw new IllegalArgumentException("O tempo de preparo deve ser, no mínimo, de 1 minuto!");
        }
        this.tempoPreparoMinutos = tempoPreparoMinutos;
    }

    public TecnicaPreparo getTecnicaPreparo() {
        return tecnicaPreparo;
    }

    @Override
    public ReceitaLanche clone() throws CloneNotSupportedException {
        ReceitaLanche receitaClone = (ReceitaLanche) super.clone();
        receitaClone.ingredientesBase = new ArrayList<Ingrediente>(this.ingredientesBase);
        return receitaClone;
    }

    @Override
    public String toString() {
        return "ReceitaLanche{" +
                "Nome:'" + nome + '\'' +
                ", Ingredientes:" + ingredientesBase +
                ", Preço Base:" + precoBase +
                ", Tempo de Preparo:" + tempoPreparoMinutos +
                ", Técnica de Preparo:" + tecnicaPreparo +
                '}';
    }
}