package test.cozinha;

import main.cardapio.*;
import main.cozinha.PreparoEmpanado;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PreparoEmpanadoTest {

    @Test
    void devePrepararLancheChickenComEtapasCorretas() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Australiano Empanado", 110, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Filé de Frango Empanado", 220, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Maionese Temperada Empanado", 60, false));
        ReceitaLanche receita = new ReceitaLanche("Frango Crispy", ingredientes, 21.90f,
                14, TecnicaPreparo.CHICKEN);

        PreparoEmpanado preparo = new PreparoEmpanado(receita);

        assertEquals("Tostar e Adicionar Pão Australiano Empanado + Empanar, Fritar e Adicionar" +
                " Filé de Frango Empanado + Adicionar Maionese Temperada Empanado +" +
                " Embalar em Papel Absorvente Para Frituras", preparo.prepararLanche());
    }

    @Test
    void deveFinalizarLancheComDadosDaReceita() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Potato Empanado", 130, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Frango Crocante Empanado", 240, false));
        ReceitaLanche receita = new ReceitaLanche("Chicken Supreme", ingredientes, 24.90f,
                16, TecnicaPreparo.CHICKEN);

        PreparoEmpanado preparo = new PreparoEmpanado(receita);

        Lanche lanche = preparo.finalizar();

        assertEquals("Chicken Supreme", lanche.getDescricao());
        assertEquals(24.90f, lanche.getPreco());
    }
}
