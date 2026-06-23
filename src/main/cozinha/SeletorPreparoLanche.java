package main.cozinha;

import main.cardapio.ReceitaLanche;
import main.cardapio.TecnicaPreparo;

public class SeletorPreparoLanche {

    public static PreparoLanche obterPreparo(ReceitaLanche receita) {
        if (receita == null) {
            throw new IllegalArgumentException("ERR01 - A receita referenciada não pode ser nula!");
        }
        return switch (receita.getTecnicaPreparo()) {
            case TRADICIONAL, ARTESANAL -> new PreparoGrelhado(receita);
            case CHICKEN -> new PreparoEmpanado(receita);
        };
    }
}
