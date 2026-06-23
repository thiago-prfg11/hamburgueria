package main.cozinha;

import main.cardapio.Ingrediente;
import main.cardapio.ReceitaLanche;

public class PreparoGrelhado extends PreparoLanche {

    public PreparoGrelhado(ReceitaLanche receita) {
        super(receita);
    }

    protected void prepararProteina(Ingrediente proteina) {
        etapas.add("Grelhar e Adicionar " + proteina.nome());
    }
}
