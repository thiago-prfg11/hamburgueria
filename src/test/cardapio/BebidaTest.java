package test.cardapio;

import main.cardapio.Bebida;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BebidaTest {

    @Test
    void deveCriarBebidaComPrecoValido() {
        Bebida bebida = new Bebida("Refrigerante Lata", 6.50f, 140);
        assertEquals(6.50f, bebida.getPreco());
    }

    @Test
    void deveCriarBebidaComCaloriasValidas() {
        Bebida bebida = new Bebida("Refrigerante Lata", 6.50f, 140);
        assertEquals(140, bebida.getCalorias());
    }

    @Test
    void deveRetornarExcecaoParaPrecoNegativo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Bebida("Suco Natural", -2.0f, 90));
        assertEquals("ERR03 - O preço da bebida não pode ser negativo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCaloriasNegativas() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Bebida("Água com Gás", 4.0f, -1));
        assertEquals("ERR03 - A quantidade de calorias da bebida não pode ser negativa!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaDescricaoVazia() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Bebida("   ", 4.0f, 100));
        assertEquals("ERR02 - A descrição do item não pode ser nula ou em branco!", e.getMessage());
    }
}
