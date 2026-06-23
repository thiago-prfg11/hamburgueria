package main.cozinha;

import main.cardapio.Ingrediente;
import main.cardapio.ReceitaLanche;

public class PreparoEmpanado extends PreparoLanche {

    public PreparoEmpanado(ReceitaLanche receita) {
        super(receita);
    }

    protected void prepararProteina(Ingrediente proteina) {
        etapas.add("Empanar, Fritar e Adicionar " + proteina.nome());
    }

    @Override
    protected void embalar() {
        etapas.add("Embalar em Papel Absorvente Para Frituras");
    }
}
