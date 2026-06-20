package test.cardapio;

import main.cardapio.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class VisitorTabelaNutricionalTest {

    @Test
    void deveExibirCaloriasParaLanche() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Visitor Nutri", 300, false));
        Lanche lanche = new Lanche("X-Nutri", 17.90f, ingredientes);
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals("Lanche: X-Nutri | Calorias: 300KCal", visitor.exibir(lanche));
    }

    @Test
    void deveExibirCaloriasParaLancheDecorado() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Visitor Nutri Ovo", 300, false));
        Lanche lanche = new Lanche("X-Nutri", 17.90f, ingredientes);
        AdicionalOvo comOvo = new AdicionalOvo(lanche);
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals("Lanche: X-Nutri + Ovo | Calorias: 370KCal", visitor.exibir(comOvo));
    }

    @Test
    void deveExibirCaloriasParaBebida() {
        Bebida suco = new Bebida("Suco Nutri", 8.0f, 90);
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals("Lanche: Suco Nutri | Calorias: 90KCal", visitor.exibir(suco));
    }

    @Test
    void deveExibirCaloriasParaAcompanhamento() {
        Acompanhamento batata = new Acompanhamento("Batata Nutri", 14.90f, 400);
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals("Lanche: Batata Nutri | Calorias: 400KCal", visitor.exibir(batata));
    }

    @Test
    void deveExibirCaloriasParaCombo() {
        Combo combo = new Combo("Combo Nutri", 0f);
        combo.addItem(new Bebida("Refrigerante", 6.0f, 140));
        combo.addItem(new Acompanhamento("Batata Família", 18.0f, 480));
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals("Lanche: Combo Nutri | Calorias: 620KCal", visitor.exibir(combo));
    }

    @Test
    void deveRetornarMesmoResultadoChamandoAceitarDiretamente() {
        Acompanhamento onionRings = new Acompanhamento("Onion Rings", 13.9f, 350);
        VisitorTabelaNutricional visitor = new VisitorTabelaNutricional();
        assertEquals(visitor.exibir(onionRings), onionRings.aceitar(visitor));
    }
}
