package main.cardapio;

import java.util.HashMap;
import java.util.Map;

public class CatalogoReceitas {

    private final Map<String, ReceitaLanche> receitas = new HashMap<>();

    public void cadastrarReceita(ReceitaLanche receita) {
        if (receita == null) {
            throw new IllegalArgumentException("A receita inserida não pode ser nula!");
        }
        this.receitas.put(receita.getNome(), receita);
    }

    public ReceitaLanche obterReceita(String nome) {
        ReceitaLanche receita = this.receitas.get(nome);
        if (receita == null) {
            throw new IllegalArgumentException("A receita buscada não foi encontrada!");
        }
        try {
            return receita.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Não foi possível clonar a receita!");
        }
    }
}
