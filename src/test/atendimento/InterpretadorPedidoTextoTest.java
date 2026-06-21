package test.atendimento;

import main.atendimento.InterpretadorExpressaoPedido;
import main.atendimento.InterpretadorPedidoTexto;
import main.cardapio.Ingrediente;
import main.cardapio.IngredienteFactory;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InterpretadorPedidoTextoTest {

    @Test
    void deveInterpretarUnicoIngrediente() {
        IngredienteFactory.getIngrediente("PaoBriocheInterpreterV2", 120, false);
        InterpretadorExpressaoPedido interpretador = new InterpretadorPedidoTexto("PaoBriocheInterpreterV2");
        assertEquals(1, interpretador.interpretar().size());
    }

    @Test
    void deveInterpretarExpressaoComAdicoes() {
        IngredienteFactory.getIngrediente("PaoBriocheInterpreterV2", 120, false);
        IngredienteFactory.getIngrediente("CarneAngusInterpreterV2", 280, false);
        IngredienteFactory.getIngrediente("BaconInterpreterV2", 150, false);
        InterpretadorExpressaoPedido interpretador = new InterpretadorPedidoTexto(
                "PaoBriocheInterpreterV2 + CarneAngusInterpreterV2 + BaconInterpreterV2");
        assertEquals(3, interpretador.interpretar().size());
    }

    @Test
    void deveInterpretarExpressaoComRemocao() {
        IngredienteFactory.getIngrediente("PaoBriocheInterpreterV2", 120, false);
        IngredienteFactory.getIngrediente("CarneAngusInterpreterV2", 280, false);
        Ingrediente cebola = IngredienteFactory.getIngrediente("CebolaInterpreterV2", 8, false);
        InterpretadorExpressaoPedido interpretador = new InterpretadorPedidoTexto(
                "PaoBriocheInterpreterV2 + CarneAngusInterpreterV2 + CebolaInterpreterV2 - CebolaInterpreterV2");
        List<Ingrediente> resultado = interpretador.interpretar();
        assertEquals(2, resultado.size());
        assertFalse(resultado.contains(cebola));
    }

    @Test
    void deveRetornarExcecaoParaElementoInvalido() {
        IngredienteFactory.getIngrediente("PaoBriocheInterpreterV2", 120, false);
        try {
            new InterpretadorPedidoTexto("PaoBriocheInterpreterV2 + IngredienteFantasmaV2");
        } catch (IllegalArgumentException e) {
            assertEquals("Expressão com elemento inválido", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaExpressaoComOperadorNoFim() {
        IngredienteFactory.getIngrediente("PaoBriocheInterpreterV2", 120, false);
        try {
            new InterpretadorPedidoTexto("PaoBriocheInterpreterV2 +");
        } catch (IllegalArgumentException e) {
            assertEquals("Expressão inválida", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaContextoNulo() {
        try {
            new InterpretadorPedidoTexto(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Expressão inválida", e.getMessage());
        }
    }
}
