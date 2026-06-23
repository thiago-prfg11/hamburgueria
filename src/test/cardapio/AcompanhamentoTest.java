package test.cardapio;

import main.cardapio.Acompanhamento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AcompanhamentoTest {

    @Test
    void deveCriarAcompanhamentoComPrecoValido() {
        Acompanhamento batata = new Acompanhamento("Batata Frita Grande", 14.90f, 480);
        assertEquals(14.90f, batata.getPreco());
    }

    @Test
    void deveCriarAcompanhamentoComCaloriasValidas() {
        Acompanhamento batata = new Acompanhamento("Batata Frita Grande", 14.90f, 480);
        assertEquals(480, batata.getCalorias());
    }

    @Test
    void deveRetornarExcecaoParaPrecoNegativo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Acompanhamento("Onion Rings", -3.0f, 350));
        assertEquals("ERR03 - O preço do acompanhamento não pode ser negativo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCaloriasNegativas() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Acompanhamento("Nuggets", 12.0f, -5));
        assertEquals("ERR03 - A quantidade de calorias do acompanhamento não pode ser negativa!", e.getMessage());
    }
}
