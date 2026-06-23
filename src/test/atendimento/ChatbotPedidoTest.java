package test.atendimento;

import main.atendimento.ChatbotPedido;
import main.cardapio.IngredienteFactory;
import main.cardapio.ItemCardapio;
import main.cardapio.Lanche;
import main.pedido.FreteFixo;
import main.pedido.Pedido;
import main.pedido.PedidoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatbotPedidoTest {

    @BeforeEach
    void setUp() {
        IngredienteFactory.limpar();
    }

    @Test
    void deveMontarLancheAPartirDeExpressaoDeTexto() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false);
        IngredienteFactory.getIngrediente("Seara – Bacon em Fatias", 150, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        Lanche lanche = chatbot.montarLanche("X-Angus", 23.90f,
                "Wickbold – Pão Brioche para Hambúrguer + Swift – Hambúrguer Angus 180g +" +
                        " Seara – Bacon em Fatias");
        assertEquals(550, lanche.getCalorias());
    }

    @Test
    void deveMontarLancheComRemocaoNaExpressaoDeTexto() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        IngredienteFactory.getIngrediente("Hortifruti Natural da Terra – Cebola Nacional", 8, false);

        ChatbotPedido chatbot = new ChatbotPedido();
        Lanche lanche = chatbot.montarLanche("X-Chatbot Sem Cebola", 18.90f,
                "Wickbold – Pão Brioche para Hambúrguer + Hortifruti Natural da Terra – Cebola Nacional -" +
                        " Hortifruti Natural da Terra – Cebola Nacional");
        assertEquals(120, lanche.getCalorias());
    }

    @Test
    void deveRetornarExcecaoParaElementoInvalidoNaExpressao() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class,
                () -> chatbot.montarLanche("X-Bread", 10.0f, "Wickbold – Pão Brioche para Hambúrguer" +
                        " + IngredienteFantasma"));
        assertEquals("ERR01 - O ingrediente referenciado não pode ser nulo!", e1.getMessage());
    }

    @Test
    void deveAdicionarLancheDeTextoDiretamenteAoPedidoBuilder() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        PedidoBuilder builder = new PedidoBuilder();
        chatbot.adicionarAoPedido(builder, "X-Bread", 15.90f, "Wickbold –" +
                " Pão Brioche para Hambúrguer");
        assertEquals(1, builder.getItensAtuais().size());
    }

    @Test
    void deveRegistrarSnapshotDeMementoAoAdicionarViaChatbot() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        PedidoBuilder builder = new PedidoBuilder();
        chatbot.adicionarAoPedido(builder, "X-Bread", 17.90f, "Wickbold – Pão Brioche para Hambúrguer");
        assertEquals(1, builder.getHistorico().size());
    }

    @Test
    void deveRetornarExcecaoParaPedidoBuilderNulo() {
        ChatbotPedido chatbot = new ChatbotPedido();
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class,
                () -> chatbot.adicionarAoPedido(null, "X-Egg", 10.0f, "Wickbold –" +
                        " Pão Brioche para Hambúrguer"));
        assertEquals("ERR01 - O builder referenciado não pode ser nulo!", e2.getMessage());
    }

    @Test
    void deveConstruirPedidoComUmUnicoItemAPartirDoChatbot() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false);
        IngredienteFactory.getIngrediente("Seara – Bacon em Fatias", 150, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        PedidoBuilder builder = new PedidoBuilder()
                .setCodigoPedido("PED-CHATBOT-001")
                .setNomeCliente("Cliente Chatbot")
                .setEstrategiaFrete(new FreteFixo());
        chatbot.adicionarAoPedido(builder, "X-Angus", 26.90f,
                "Wickbold – Pão Brioche para Hambúrguer + Swift – Hambúrguer Angus 180g + Seara – Bacon em Fatias");

        Pedido pedido = builder.build();

        assertEquals(1, pedido.getItens().size());
    }

    @Test
    void deveConstruirPedidoComDescricaoCorretaAPartirDoChatbot() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false);
        IngredienteFactory.getIngrediente("Seara – Bacon em Fatias", 150, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        PedidoBuilder builder = new PedidoBuilder()
                .setCodigoPedido("PED-CHATBOT-002")
                .setNomeCliente("Cliente Chatbot")
                .setEstrategiaFrete(new FreteFixo());
        chatbot.adicionarAoPedido(builder, "X-Angus", 26.90f,
                "Wickbold – Pão Brioche para Hambúrguer + Swift – Hambúrguer Angus 180g + Seara – Bacon em Fatias");

        Pedido pedido = builder.build();
        ItemCardapio item = pedido.getItens().getFirst();

        assertEquals("X-Angus", item.getDescricao());
    }

    @Test
    void deveConstruirPedidoComCaloriasCorretasAPartirDoChatbot() {
        IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false);
        IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false);
        IngredienteFactory.getIngrediente("Seara – Bacon em Fatias", 150, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        PedidoBuilder builder = new PedidoBuilder()
                .setCodigoPedido("PED-CHATBOT-003")
                .setNomeCliente("Cliente Chatbot")
                .setEstrategiaFrete(new FreteFixo());
        chatbot.adicionarAoPedido(builder, "X-Angus", 26.90f,
                "Wickbold – Pão Brioche para Hambúrguer + Swift – Hambúrguer Angus 180g + Seara – Bacon em Fatias");

        Pedido pedido = builder.build();
        ItemCardapio item = pedido.getItens().getFirst();

        assertEquals(550, item.getCalorias());
    }
}
