package main.cardapio;

public class Ingrediente {

    private final String nome;
    private final int calorias;
    private final boolean alergenico;

    public Ingrediente(String nome, int calorias, boolean alergenico) {
        this.nome = nome;
        this.calorias = calorias;
        this.alergenico = alergenico;
    }

    public String getNome() {
        return nome;
    }

    public int getCalorias() {
        return calorias;
    }

    public boolean isAlergenico() {
        return alergenico;
    }
}
