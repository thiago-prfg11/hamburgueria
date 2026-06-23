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
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        InterpretadorExpressaoPedido interpretador = new InterpretadorPedidoTexto("Wickbold – Pão Brioche para Hambúrguer");
        assertEquals(1, interpretador.interpretar().size());
    }

    @Test
    void deveInterpretarExpressaoComAdicoes() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false);
        IngredienteFactory.getIngrediente("Seara – Bacon em Fatias", 150, false);
        InterpretadorExpressaoPedido interpretador = new InterpretadorPedidoTexto(
                "Wickbold – Pão Brioche para Hambúrguer + Swift – Hambúrguer Angus 180g + Seara – Bacon em Fatias");
        assertEquals(3, interpretador.interpretar().size());
    }

    @Test
    void deveInterpretarExpressaoComRemocao() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false);
        Ingrediente cebola = IngredienteFactory.getIngrediente("Hortifruti Natural da Terra – Cebola Nacional", 8, false);
        InterpretadorExpressaoPedido interpretador = new InterpretadorPedidoTexto(
                "Wickbold – Pão Brioche para Hambúrguer + Swift – Hambúrguer Angus 180g + Hortifruti Natural da Terra – Cebola Nacional - Hortifruti Natural da Terra – Cebola Nacional");
        List<Ingrediente> resultado = interpretador.interpretar();
        assertEquals(2, resultado.size());
        assertFalse(resultado.contains(cebola));
    }

    @Test
    void deveRetornarExcecaoParaElementoInvalido() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new InterpretadorPedidoTexto("Wickbold – Pão Brioche para Hambúrguer + IngredienteFantasmaV2"));
        assertEquals("ERR01 - O ingrediente referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaExpressaoComOperadorNoFim() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new InterpretadorPedidoTexto("Wickbold – Pão Brioche para Hambúrguer +"));
        assertEquals("ERR01 - O ingrediente referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaContextoNulo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new InterpretadorPedidoTexto(null));
        assertEquals("ERR02 - A Expressão referenciada não pode ser nula ou em branco!", e.getMessage());
    }
}
