package test.cardapio;

import main.cardapio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class VisitorTabelaNutricionalTest {

    @BeforeEach
    void setUp() {
        IngredienteFactory.limpar();
    }

    @Test
    void deveExibirCaloriasParaLanche() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 300, false));
        Lanche lanche = new Lanche("X-Tião", 17.90f, ingredientes);
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals("Item: X-Tião | Calorias: 300KCal", visitor.exibir(lanche));
    }

    @Test
    void deveExibirCaloriasParaLancheDecorado() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 300, false));
        Lanche lanche = new Lanche("X-Bacon", 17.90f, ingredientes);
        AdicionalOvo comOvo = new AdicionalOvo(lanche);
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals("Item: X-Bacon + Ovo | Calorias: 370KCal", visitor.exibir(comOvo));
    }

    @Test
    void deveExibirCaloriasParaBebida() {
        Bebida suco = new Bebida("Maguary – Suco de Laranja", 8.0f, 90);
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals("Item: Maguary – Suco de Laranja | Calorias: 90KCal", visitor.exibir(suco));
    }

    @Test
    void deveExibirCaloriasParaAcompanhamento() {
        Acompanhamento batata = new Acompanhamento("Batata Média", 14.90f, 400);
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals("Item: Batata Média | Calorias: 400KCal", visitor.exibir(batata));
    }

    @Test
    void deveExibirCaloriasParaCombo() {
        Combo combo = new Combo("Combo Kids", 0f);
        combo.addItem(new Bebida("Pepsi – Lata 350 ml", 6.0f, 140));
        combo.addItem(new Acompanhamento("Batata Pequena", 18.0f, 480));
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals("Item: Combo Kids | Calorias: 620KCal", visitor.exibir(combo));
    }

    @Test
    void deveRetornarMesmoResultadoChamandoAceitarDiretamente() {
        Acompanhamento onionRings = new Acompanhamento("Onion Rings", 13.9f, 350);
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals(visitor.exibir(onionRings), onionRings.aceitar(visitor));
    }
}
