package test.cardapio;

import main.cardapio.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class VisitorImpressaoCupomTest {

    @Test
    void deveImprimirLinhaParaLanche() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Visitor Cupom", 150, false));
        Lanche lanche = new Lanche("X-Visitor", 18.90f, ingredientes);
        VisitorImpressaoCupom visitor = new VisitorImpressaoCupom();
        assertEquals("X-Visitor ... R$ 18.90", visitor.imprimir(lanche));
    }

    @Test
    void deveImprimirLinhaParaLancheDecorado() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Visitor Cupom Bacon", 150, false));
        Lanche lanche = new Lanche("X-Visitor", 18.90f, ingredientes);
        AdicionalBacon comBacon = new AdicionalBacon(lanche);
        VisitorImpressaoCupom visitor = new VisitorImpressaoCupom();
        assertEquals("X-Visitor + Bacon ... R$ 23.40", visitor.imprimir(comBacon));
    }

    @Test
    void deveImprimirLinhaParaBebida() {
        Bebida bebida = new Bebida("Refrigerante Visitor", 6.50f, 140);
        VisitorImpressaoCupom visitor = new VisitorImpressaoCupom();
        assertEquals("Refrigerante Visitor ... R$ 6.50", visitor.imprimir(bebida));
    }

    @Test
    void deveImprimirLinhaParaAcompanhamento() {
        Acompanhamento batata = new Acompanhamento("Batata Visitor", 14.90f, 480);
        VisitorImpressaoCupom visitor = new VisitorImpressaoCupom();
        assertEquals("Batata Visitor ... R$ 14.90", visitor.imprimir(batata));
    }

    @Test
    void deveImprimirLinhaParaCombo() {
        Combo combo = new Combo("Combo Visitor", 0f);
        combo.addItem(new Bebida("Refrigerante", 6.0f, 140));
        combo.addItem(new Acompanhamento("Batata Média", 10.0f, 400));
        VisitorImpressaoCupom visitor = new VisitorImpressaoCupom();
        assertEquals("Combo Visitor ... R$ 16.00", visitor.imprimir(combo));
    }

    @Test
    void deveRetornarMesmoResultadoChamandoAceitarDiretamente() {
        Bebida cha = new Bebida("Chá Gelado", 5.5f, 60);
        VisitorImpressaoCupom visitor = new VisitorImpressaoCupom();
        assertEquals(visitor.imprimir(cha), cha.aceitar(visitor));
    }
}
