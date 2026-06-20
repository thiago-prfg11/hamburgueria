package test.cardapio;

import main.cardapio.Acompanhamento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AcompanhamentoTest {

    @Test
    void deveCriarAcompanhamentoValido() {
        Acompanhamento batata = new Acompanhamento("Batata Frita Grande", 14.90f, 480);
        assertEquals(14.90f, batata.getPreco());
        assertEquals(480, batata.getCalorias());
    }

    @Test
    void deveRetornarExcecaoParaPrecoNegativo() {
        try {
            new Acompanhamento("Onion Rings", -3.0f, 350);
        } catch (IllegalArgumentException e) {
            assertEquals("O preço do acompanhamento é inválido!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCaloriasNegativas() {
        try {
            new Acompanhamento("Nuggets", 12.0f, -5);
        } catch (IllegalArgumentException e) {
            assertEquals("A quantidade de calorias do acompanhamento é inválido!", e.getMessage());
        }
    }
}
