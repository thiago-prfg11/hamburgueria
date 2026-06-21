package test.cardapio;

import main.cardapio.Ingrediente;
import main.cardapio.IngredienteFactory;
import main.cardapio.ReceitaLanche;
import main.cardapio.TecnicaPreparo;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReceitaLancheTest {

    @Test
    void deveManterListaIndependenteDaListaOriginalPassadaNoConstrutor() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Australiano", 120, false));
        ReceitaLanche receita = new ReceitaLanche("X-Bacon", ingredientes, 18.90f, 12,
                TecnicaPreparo.TRADICIONAL);

        ingredientes.add(IngredienteFactory.getIngrediente("Bacon Artesanal", 150, false));

        assertEquals(1, receita.getIngredientesBase().size());
    }

    @Test
    void deveRetornarCopiaIndependenteAoChamarGetIngredientesBase() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Getter Independente", 120, false));
        ReceitaLanche receita = new ReceitaLanche("X-Getter", ingredientes, 17.90f, 10,
                TecnicaPreparo.TRADICIONAL);

        receita.getIngredientesBase().add(IngredienteFactory.getIngrediente("Ingrediente Vazado", 50, false));

        assertEquals(1, receita.getIngredientesBase().size());
    }

    @Test
    void deveManterMesmaInstanciaDeIngredienteAposClone() throws CloneNotSupportedException {
        List<Ingrediente> ingredientes = new ArrayList<>();
        Ingrediente paoMultigraos = IngredienteFactory.getIngrediente("Pão Multigrãos", 110, false);
        ingredientes.add(paoMultigraos);
        ReceitaLanche receitaOriginal = new ReceitaLanche("X-Salada", ingredientes, 16.90f,
                10, TecnicaPreparo.TRADICIONAL);

        ReceitaLanche receitaClone = receitaOriginal.clone();

        assertSame(paoMultigraos, receitaClone.getIngredientesBase().get(0));
    }

    @Test
    void deveRetornarListaDeIngredientesIndependenteEntreOriginalEClone() throws CloneNotSupportedException {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Clone Independente", 120, false));
        ReceitaLanche receitaOriginal = new ReceitaLanche("X-Clone", ingredientes, 19.90f,
                11, TecnicaPreparo.TRADICIONAL);

        ReceitaLanche receitaClone = receitaOriginal.clone();

        assertNotSame(receitaOriginal.getIngredientesBase(), receitaClone.getIngredientesBase());
    }

    @Test
    void deveRetornarExcecaoParaNomeNuloNoConstrutor() {
        try {
            new ReceitaLanche(null, new ArrayList<>(), 10.0f, 10,
                    TecnicaPreparo.TRADICIONAL);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O nome da receita não pode ser nulo ou em branco!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaNomeVazioNoConstrutor() {
        try {
            new ReceitaLanche("   ", new ArrayList<>(), 10.0f, 10,
                    TecnicaPreparo.TRADICIONAL);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O nome da receita não pode ser nulo ou em branco!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaListaIngredientesNulaNoConstrutor() {
        try {
            new ReceitaLanche("X-Tudo", null, 10.0f, 10,
                    TecnicaPreparo.TRADICIONAL);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("A lista de ingredientes não pode ser nula!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaPrecoBaseNegativoNoConstrutor() {
        try {
            new ReceitaLanche("X-Tudo", new ArrayList<>(), -5.0f, 10,
                    TecnicaPreparo.TRADICIONAL);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O preço base não pode ser negativo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaTempoPreparoInvalidoNoConstrutor() {
        try {
            new ReceitaLanche("X-Tudo", new ArrayList<>(), 10.0f, 0,
                    TecnicaPreparo.TRADICIONAL);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O tempo de preparo deve ser, no mínimo, de 1 minuto!", e.getMessage());
        }
    }

    @Test
    void deveCriarReceitaComTecnicaPreparoChicken() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Chicken Teste", 110, false));
        ReceitaLanche receita = new ReceitaLanche("Frango Crispy", ingredientes, 21.90f,
                14, TecnicaPreparo.CHICKEN);

        assertEquals(TecnicaPreparo.CHICKEN, receita.getTecnicaPreparo());
    }

    @Test
    void deveRetornarExcecaoParaTecnicaPreparoNulaNoConstrutor() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Tecnica Nula", 120, false));
        try {
            new ReceitaLanche("X-Erro", ingredientes, 18.0f, 10, null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("A técnica de preparo não pode ser nula!", e.getMessage());
        }
    }
}
