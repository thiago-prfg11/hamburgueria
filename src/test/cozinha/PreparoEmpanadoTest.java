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
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 110, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Perdigão – Filé de Frango Empanado", 220, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Hellmann's – Maionese com Alho", 60, false));
        ReceitaLanche receita = new ReceitaLanche("X-Crispy", ingredientes, 21.90f,
                14, TecnicaPreparo.CHICKEN);

        PreparoEmpanado preparo = new PreparoEmpanado(receita);

        assertEquals("Tostar e Adicionar Wickbold – Pão Brioche para Hambúrguer + Empanar, Fritar e Adicionar" +
                " Perdigão – Filé de Frango Empanado + Adicionar Hellmann's – Maionese com Alho +" +
                " Embalar em Papel Absorvente Para Frituras", preparo.prepararLanche());
    }

    @Test
    void deveFinalizarLancheComDescricaoDaReceita() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 130, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Perdigão – Filé de Frango Empanado", 240, false));
        ReceitaLanche receita = new ReceitaLanche("Chicken Supreme", ingredientes, 24.90f,
                16, TecnicaPreparo.CHICKEN);

        PreparoEmpanado preparo = new PreparoEmpanado(receita);

        assertEquals("Chicken Supreme", preparo.finalizar().getDescricao());
    }

    @Test
    void deveFinalizarLancheComPrecoDaReceita() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 130, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Perdigão – Filé de Frango Empanado", 240, false));
        ReceitaLanche receita = new ReceitaLanche("Chicken Supreme", ingredientes, 24.90f,
                16, TecnicaPreparo.CHICKEN);

        PreparoEmpanado preparo = new PreparoEmpanado(receita);

        assertEquals(24.90f, preparo.finalizar().getPreco());
    }
}
