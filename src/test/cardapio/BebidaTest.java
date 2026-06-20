package test.cardapio;

import main.cardapio.Bebida;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BebidaTest {

    @Test
    void deveCriarBebidaValida() {
        Bebida bebida = new Bebida("Refrigerante Lata", 6.50f, 140);
        assertEquals(6.50f, bebida.getPreco());
        assertEquals(140, bebida.getCalorias());
    }

    @Test
    void deveRetornarExcecaoParaPrecoNegativo() {
        try {
            new Bebida("Suco Natural", -2.0f, 90);
        } catch (IllegalArgumentException e) {
            assertEquals("O preço da bebida é inválido!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCaloriasNegativas() {
        try {
            new Bebida("Água com Gás", 4.0f, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("A quantidade de calorias da bebida é inválida!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaDescricaoVazia() {
        try {
            new Bebida("   ", 4.0f, 100);
        } catch (IllegalArgumentException e) {
            assertEquals("A descrição do item é inválida!", e.getMessage());
        }
    }
}
