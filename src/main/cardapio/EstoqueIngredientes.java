package main.cardapio;

import java.util.HashMap;
import java.util.Map;

public class EstoqueIngredientes {

    private final Map<Ingrediente, Integer> quantidades = new HashMap<>();

    public void registrarEntrada(String nome, int calorias, boolean alergenico, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade inserida para o ingrediente deve ser maior que 0!");
        }
        Ingrediente ingrediente = IngredienteFactory.getIngrediente(nome, calorias, alergenico);
        int quantidadeAtual = quantidades.getOrDefault(ingrediente, 0);
        quantidades.put(ingrediente, quantidadeAtual + quantidade);
    }

    public void registrarSaida(String nome, int quantidade) {
        Ingrediente ingrediente = buscarPorNome(nome);
        if (ingrediente == null) {
            throw new IllegalArgumentException("O ingrediente selecionado não foi encontrado no estoque!");
        }
        int quantidadeAtual = quantidades.get(ingrediente);
        if (quantidade > quantidadeAtual) {
            throw new IllegalArgumentException("A quantidade selecionada para baixa é inferior à quantidade em estoque!");
        }
        quantidades.put(ingrediente, quantidadeAtual - quantidade);
    }

    public int getQuantidadeDisponivel(String nome) {
        Ingrediente ingrediente = buscarPorNome(nome);
        if (ingrediente == null) {
            return 0;
        }
        return quantidades.get(ingrediente);
    }

    private Ingrediente buscarPorNome(String nome) {
        for (Ingrediente ingrediente : quantidades.keySet()) {
            if (ingrediente.getNome().equals(nome)) {
                return ingrediente;
            }
        }
        return null;
    }
}
