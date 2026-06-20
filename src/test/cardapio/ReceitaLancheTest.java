package test.cardapio;

import main.cardapio.Ingrediente;
import main.cardapio.IngredienteFactory;
import main.cardapio.ReceitaLanche;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReceitaLancheTest {

    @Test
    void deveClonarReceitaComListaIndependente() throws CloneNotSupportedException {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Australiano", 120, false));
        ReceitaLanche receitaOriginal = new ReceitaLanche("X-Bacon", ingredientes, 18.90f, 12);
        ReceitaLanche receitaClone = receitaOriginal.clone();
        receitaClone.adicionarIngrediente(IngredienteFactory.getIngrediente("Bacon Artesanal", 150, false));
        assertEquals(1, receitaOriginal.getIngredientesBase().size());
        assertEquals(2, receitaClone.getIngredientesBase().size());
    }

    @Test
    void deveManterMesmaInstanciaDeIngredienteAposClone() throws CloneNotSupportedException {
        List<Ingrediente> ingredientes = new ArrayList<>();
        Ingrediente paoMultigraos = IngredienteFactory.getIngrediente("Pão Multigrãos", 110, false);
        ingredientes.add(paoMultigraos);
        ReceitaLanche receitaOriginal = new ReceitaLanche("X-Salada", ingredientes, 16.90f, 10);
        ReceitaLanche receitaClone = receitaOriginal.clone();
        assertSame(paoMultigraos, receitaClone.getIngredientesBase().get(0));
    }

    @Test
    void deveRetornarExcecaoParaNomeNuloNoConstrutor() {
        try {
            new ReceitaLanche(null, new ArrayList<>(), 10.0f, 10);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O nome da receita inserido não pode ser nulo ou em branco!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaNomeVazioNoConstrutor() {
        try {
            new ReceitaLanche("   ", new ArrayList<>(), 10.0f, 10);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O nome da receita inserido não pode ser nulo ou em branco!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaListaIngredientesNulaNoConstrutor() {
        try {
            new ReceitaLanche("X-Tudo", null, 10.0f, 10);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("A lista de ingredientes inserida não pode ser nula!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaPrecoBaseNegativoNoConstrutor() {
        try {
            new ReceitaLanche("X-Tudo", new ArrayList<>(), -5.0f, 10);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O preço base inserido não pode ser negativo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaTempoPreparoInvalidoNoConstrutor() {
        try {
            new ReceitaLanche("X-Tudo", new ArrayList<>(), 10.0f, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O tempo de preparo inserido não pode ser negativo!", e.getMessage());
        }
    }

    @Test
    void deveAdicionarIngrediente() {
        ReceitaLanche receita = new ReceitaLanche("X-Egg", new ArrayList<>(), 14.90f, 8);
        receita.adicionarIngrediente(IngredienteFactory.getIngrediente("Ovo Caipira", 70, false));
        assertEquals(1, receita.getIngredientesBase().size());
    }

    @Test
    void deveRetornarExcecaoParaAdicionarIngredienteNulo() {
        ReceitaLanche receita = new ReceitaLanche("X-Egg", new ArrayList<>(), 14.90f, 8);
        try {
            receita.adicionarIngrediente(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O ingrediente inserido não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRemoverIngrediente() {
        Ingrediente cebolaRoxa = IngredienteFactory.getIngrediente("Cebola Roxa", 8, false);
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(cebolaRoxa);
        ReceitaLanche receita = new ReceitaLanche("X-Burger", ingredientes, 15.90f, 9);
        receita.removerIngrediente(cebolaRoxa);
        assertTrue(receita.getIngredientesBase().isEmpty());
    }

    @Test
    void deveManterListaInalteradaAoRemoverIngredienteInexistente() {
        Ingrediente cebolaBranca = IngredienteFactory.getIngrediente("Cebola Branca", 8, false);
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(cebolaBranca);
        ReceitaLanche receita = new ReceitaLanche("X-Frango", ingredientes, 15.90f, 9);
        receita.removerIngrediente(IngredienteFactory.getIngrediente("Picles Inexistente", 4, false));
        assertEquals(1, receita.getIngredientesBase().size());
    }
}
