package test.cozinha;

import main.cardapio.Ingrediente;
import main.cardapio.IngredienteFactory;
import main.cardapio.ReceitaLanche;
import main.cardapio.TecnicaPreparo;
import main.cozinha.PreparoEmpanado;
import main.cozinha.PreparoGrelhado;
import main.cozinha.PreparoLanche;
import main.cozinha.SeletorPreparoLanche;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SeletorPreparoLancheTest {

    @Test
    void deveRetornarPreparoGrelhadoParaTecnicaTradicional() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 250, false));
        ReceitaLanche receita = new ReceitaLanche("Tradicional da Casa", ingredientes, 20.0f,
                10, TecnicaPreparo.TRADICIONAL);

        PreparoLanche preparo = SeletorPreparoLanche.obterPreparo(receita);

        assertTrue(preparo instanceof PreparoGrelhado);
    }

    @Test
    void deveRetornarPreparoGrelhadoParaTecnicaArtesanal() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 150, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 300, false));
        ReceitaLanche receita = new ReceitaLanche("X-Arte", ingredientes, 28.0f,
                14, TecnicaPreparo.ARTESANAL);

        PreparoLanche preparo = SeletorPreparoLanche.obterPreparo(receita);

        assertTrue(preparo instanceof PreparoGrelhado);
    }

    @Test
    void deveRetornarPreparoEmpanadoParaTecnicaChicken() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 110, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Seara – Supreme Crispy", 220, false));
        ReceitaLanche receita = new ReceitaLanche("X-Crispy", ingredientes, 22.0f,
                12, TecnicaPreparo.CHICKEN);

        PreparoLanche preparo = SeletorPreparoLanche.obterPreparo(receita);

        assertTrue(preparo instanceof PreparoEmpanado);
    }

    @Test
    void deveRetornarExcecaoParaReceitaNula() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> SeletorPreparoLanche.obterPreparo(null));
        assertEquals("ERR01 - A receita referenciada não pode ser nula!", e.getMessage());
    }
}
