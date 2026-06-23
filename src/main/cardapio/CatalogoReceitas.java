package main.cardapio;

import java.util.HashMap;
import java.util.Map;

public class CatalogoReceitas {

    private final Map<String, ReceitaLanche> receitas = new HashMap<>();

    public void cadastrarReceita(ReceitaLanche receita) {
        if (receita == null) {
            throw new IllegalArgumentException("ERR01 - A receita referenciada não pode ser nula!");
        }
        this.receitas.put(receita.getNome(), receita);
    }

    public ReceitaLanche obterReceita(String nome) {
        ReceitaLanche receita = this.receitas.get(nome);
        if (receita == null) {
            throw new IllegalArgumentException("ERR06 - A receita buscada não existe no sistema!");
        }
        try {
            return receita.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("ERR08 - Não foi possível clonar a receita!");
        }
    }
}
