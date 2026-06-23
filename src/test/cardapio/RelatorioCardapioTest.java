package test.cardapio;

import main.cardapio.Acompanhamento;
import main.cardapio.Bebida;
import main.cardapio.Cardapio;
import main.cardapio.RelatorioCardapio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioCardapioTest {

    Bebida refrigerante = new Bebida("Refrigerante", 6.0f, 140);
    Bebida suco = new Bebida("Suco Natural", 8.0f, 90);
    Acompanhamento batata = new Acompanhamento("Batata Frita", 14.9f, 480);
    Acompanhamento onionRings = new Acompanhamento("Onion Rings", 13.9f, 350);

    @Test
    void deveContarItensDisponiveis() {
        onionRings.setDisponivel(false);
        Cardapio cardapio = new Cardapio(refrigerante, batata, onionRings, suco);
        assertEquals(3, RelatorioCardapio.contarItensDisponiveis(cardapio));
    }

    @Test
    void deveContarTotalItens() {
        onionRings.setDisponivel(false);
        Cardapio cardapio = new Cardapio(refrigerante, batata, onionRings, suco);
        assertEquals(4, RelatorioCardapio.contarTotalItens(cardapio));
    }

    @Test
    void deveContarZeroItensDisponiveisParaCardapioTodoIndisponivel() {
        Bebida agua = new Bebida("Água Mineral", 4.0f, 0);
        agua.setDisponivel(false);

        Cardapio cardapio = new Cardapio(agua);
        assertEquals(0, RelatorioCardapio.contarItensDisponiveis(cardapio));
    }

    @Test
    void deveContarZeroItensParaCardapioVazio() {
        Cardapio cardapio = new Cardapio();
        assertEquals(0, RelatorioCardapio.contarTotalItens(cardapio));
    }
}
