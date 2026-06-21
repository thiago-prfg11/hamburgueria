package main.cozinha;

import main.cardapio.ReceitaLanche;
import main.cardapio.TecnicaPreparo;

public class SeletorPreparoLanche {

    public static PreparoLanche obterPreparo(ReceitaLanche receita) {
        if (receita == null) {
            throw new IllegalArgumentException("A receita não pode ser nula!");
        }
        TecnicaPreparo tecnica = receita.getTecnicaPreparo();
        if (tecnica == TecnicaPreparo.TRADICIONAL || tecnica == TecnicaPreparo.ARTESANAL) {
            return new PreparoGrelhado(receita);
        }
        if (tecnica == TecnicaPreparo.CHICKEN) {
            return new PreparoEmpanado(receita);
        }
        throw new IllegalArgumentException("Técnica de preparo desconhecida!");
    }
}
