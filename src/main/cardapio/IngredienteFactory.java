package main.cardapio;

import java.util.HashMap;
import java.util.Map;

public class IngredienteFactory {

    private static final Map<String, Ingrediente> ingredientes = new HashMap<>();

    public static Ingrediente getIngrediente(String nome, int calorias, boolean alergenico) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do ingrediente inserido não pode ser nulo ou em branco!");
        }
        if (calorias < 0) {
            throw new IllegalArgumentException("A quantidade de calorias inserida não pode ser negativa!");
        }
        Ingrediente ingrediente = ingredientes.get(nome);
        if (ingrediente == null) {
            ingrediente = new Ingrediente(nome, calorias, alergenico);
            ingredientes.put(nome, ingrediente);
        }
        return ingrediente;
    }

    public static int getTotalIngredientes() {
        return ingredientes.size();
    }
}
