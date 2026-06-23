package test.cardapio;

import main.cardapio.Ingrediente;
import main.cardapio.IngredienteFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IngredienteFactoryTest {

    @BeforeEach
    void setUp() {
        IngredienteFactory.limpar();
    }

    @Test
    void deveRetornarMesmaInstanciaParaMesmoNome() {
        Ingrediente ingrediente1 = IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer",
                120, false);
        Ingrediente ingrediente2 = IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer",
                999, true);
        assertSame(ingrediente1, ingrediente2);
    }

    @Test
    void deveManterCaloriasOriginaisAoTentarSobrescrever() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        Ingrediente ingrediente2 = IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer",
                999, true);
        assertEquals(120, ingrediente2.calorias());
    }

    @Test
    void deveRetornarIngredientesDiferentesParaNomesDiferentes() {
        Ingrediente ingrediente1 = IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280,
                false);
        Ingrediente ingrediente2 = IngredienteFactory.getIngrediente("Seara – Bacon em Fatias", 150,
                false);
        assertNotSame(ingrediente1, ingrediente2);
    }

    @Test
    void deveRetornarExcecaoParaNomeNulo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> IngredienteFactory.getIngrediente(null, 100, false));
        assertEquals("ERR02 - O nome do ingrediente inserido não pode ser nulo ou em branco!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaNomeVazio() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> IngredienteFactory.getIngrediente("   ", 100, false));
        assertEquals("ERR02 - O nome do ingrediente inserido não pode ser nulo ou em branco!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCaloriasNegativas() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> IngredienteFactory.getIngrediente("Cebola Caramelizada", -10, false));
        assertEquals("ERR03 - A quantidade de calorias inserida não pode ser negativa!", e.getMessage());
    }

    @Test
    void deveRetornarTotalIngredientesAposNovosCadastros() {
        int totalAntes = IngredienteFactory.getTotalIngredientes();
        IngredienteFactory.getIngrediente("Hortifruti Natural da Terra – Cebola Nacional", 8, false);
        IngredienteFactory.getIngrediente("Tirolez – Queijo Coalho", 3, false);
        int totalDepois = IngredienteFactory.getTotalIngredientes();
        assertEquals(totalAntes + 2, totalDepois);
    }

    @Test
    void deveRetornarIngredienteCadastradoAoBuscarPorNome() {
        Ingrediente ingrediente = IngredienteFactory.getIngrediente("Tirolez – Queijo Parmesão Ralado", 4,
                false);
        assertSame(ingrediente, IngredienteFactory.buscarPorNome("Tirolez – Queijo Parmesão Ralado"));
    }

    @Test
    void deveRetornarNuloParaIngredienteNaoCadastradoAoBuscarPorNome() {
        assertNull(IngredienteFactory.buscarPorNome("Ingrediente Nunca Cadastrado"));
    }
}
