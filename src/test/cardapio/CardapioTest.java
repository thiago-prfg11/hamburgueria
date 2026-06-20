package test.cardapio;

import main.cardapio.Acompanhamento;
import main.cardapio.Bebida;
import main.cardapio.Cardapio;
import main.cardapio.ItemCardapio;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CardapioTest {

    @Test
    void deveIterarSobreItensNaOrdemDeInsercao() {
        Bebida refrigerante = new Bebida("Refrigerante", 6.0f, 140);
        Acompanhamento batata = new Acompanhamento("Batata Frita", 14.9f, 480);
        Cardapio cardapio = new Cardapio(refrigerante, batata);
        List<ItemCardapio> itensPercorridos = new ArrayList<>();
        for (ItemCardapio item : cardapio) {
            itensPercorridos.add(item);
        }
        assertEquals(List.of(refrigerante, batata), itensPercorridos);
    }

    @Test
    void deveRetornarExcecaoParaItensNulos() {
        try {
            new Cardapio((ItemCardapio[]) null);
        } catch (IllegalArgumentException e) {
            assertEquals("A lista de itens no cardápio não pode ser nula!", e.getMessage());
        }
    }
}
