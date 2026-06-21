package test.cardapio;

import main.cardapio.Ingrediente;
import main.cardapio.IngredienteFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IngredienteFactoryTest {

    @Test
    void deveRetornarMesmoIngredienteParaMesmoNome() {
        Ingrediente ingrediente1 = IngredienteFactory.getIngrediente("Pão Brioche", 120, false);
        Ingrediente ingrediente2 = IngredienteFactory.getIngrediente("Pão Brioche", 999, true);
        assertSame(ingrediente1, ingrediente2);
        assertEquals(120, ingrediente2.getCalorias());
    }

    @Test
    void deveRetornarIngredientesDiferentesParaNomesDiferentes() {
        Ingrediente ingrediente1 = IngredienteFactory.getIngrediente("Carne Angus 180g", 280, false);
        Ingrediente ingrediente2 = IngredienteFactory.getIngrediente("Bacon", 150, false);
        assertNotSame(ingrediente1, ingrediente2);
    }

    @Test
    void deveRetornarExcecaoParaNomeNulo() {
        try {
            IngredienteFactory.getIngrediente(null, 100, false);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O nome do ingrediente inserido não pode ser nulo ou em branco!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaNomeVazio() {
        try {
            IngredienteFactory.getIngrediente("   ", 100, false);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O nome do ingrediente inserido não pode ser nulo ou em branco!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCaloriasNegativas() {
        try {
            IngredienteFactory.getIngrediente("Cebola Caramelizada", -10, false);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("A quantidade de calorias inserida não pode ser negativa!", e.getMessage());
        }
    }

    @Test
    void deveRetornarTotalIngredientesAposNovosCadastros() {
        int totalAntes = IngredienteFactory.getTotalIngredientes();
        IngredienteFactory.getIngrediente("Rabanete Exclusivo Factory", 8, false);
        IngredienteFactory.getIngrediente("Nirá Exclusivo Factory", 3, false);
        int totalDepois = IngredienteFactory.getTotalIngredientes();
        assertEquals(totalAntes + 2, totalDepois);
    }

    @Test
    void deveRetornarIngredienteCadastradoAoBuscarPorNome() {
        Ingrediente ingrediente = IngredienteFactory.getIngrediente("Picles Busca", 4, false);

        assertSame(ingrediente, IngredienteFactory.buscarPorNome("Picles Busca"));
    }

    @Test
    void deveRetornarNuloParaIngredienteNaoCadastradoAoBuscarPorNome() {
        assertNull(IngredienteFactory.buscarPorNome("Ingrediente Nunca Cadastrado"));
    }
}
