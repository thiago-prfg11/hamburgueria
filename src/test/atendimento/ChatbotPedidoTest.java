package test.atendimento;

import main.atendimento.ChatbotPedido;
import main.cardapio.IngredienteFactory;
import main.cardapio.ItemCardapio;
import main.cardapio.Lanche;
import main.pedido.FreteFixo;
import main.pedido.Pedido;
import main.pedido.PedidoBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatbotPedidoTest {

    @Test
    void deveMontarLancheAPartirDeExpressaoDeTexto() {
        IngredienteFactory.getIngrediente("PaoChatbot", 120, false);
        IngredienteFactory.getIngrediente("CarneChatbot", 280, false);
        IngredienteFactory.getIngrediente("BaconChatbot", 150, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        Lanche lanche = chatbot.montarLanche("X-Chatbot", 23.90f,
                "PaoChatbot + CarneChatbot + BaconChatbot");
        assertEquals(550, lanche.getCalorias());
    }

    @Test
    void deveMontarLancheComRemocaoNaExpressaoDeTexto() {
        IngredienteFactory.getIngrediente("PaoChatbotDois", 120, false);
        IngredienteFactory.getIngrediente("CebolaChatbotDois", 8, false);

        ChatbotPedido chatbot = new ChatbotPedido();
        Lanche lanche = chatbot.montarLanche("X-Chatbot Sem Cebola", 18.90f,
                "PaoChatbotDois + CebolaChatbotDois - CebolaChatbotDois");
        assertEquals(120, lanche.getCalorias());
    }

    @Test
    void deveRetornarExcecaoParaElementoInvalidoNaExpressao() {
        IngredienteFactory.getIngrediente("PaoChatbotTres", 120, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        try {
            chatbot.montarLanche("X-Erro", 10.0f, "PaoChatbotTres + IngredienteFantasmaChatbot");
        } catch (IllegalArgumentException e) {
            assertEquals("Expressão com elemento inválido", e.getMessage());
        }
    }

    @Test
    void deveAdicionarLancheDeTextoDiretamenteAoPedidoBuilder() {
        IngredienteFactory.getIngrediente("PaoChatbotBuilder", 120, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        PedidoBuilder builder = new PedidoBuilder();
        chatbot.adicionarAoPedido(builder, "X-Chatbot Builder", 15.90f, "PaoChatbotBuilder");
        assertEquals(1, builder.getItensAtuais().size());
    }

    @Test
    void deveRegistrarSnapshotDeMementoAoAdicionarViaChatbot() {
        IngredienteFactory.getIngrediente("PaoChatbotMemento", 120, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        PedidoBuilder builder = new PedidoBuilder();
        chatbot.adicionarAoPedido(builder, "X-Memento", 17.90f, "PaoChatbotMemento");
        assertEquals(1, builder.getHistorico().size());
    }

    @Test
    void deveRetornarExcecaoParaPedidoBuilderNulo() {
        ChatbotPedido chatbot = new ChatbotPedido();
        try {
            chatbot.adicionarAoPedido(null, "X-Erro", 10.0f, "Qualquer");
        } catch (IllegalArgumentException e) {
            assertEquals("Builder de pedido inválido", e.getMessage());
        }
    }

    @Test
    void deveConstruirPedidoCompletoAPartirDeExpressaoDeTextoDoChatbot() {
        IngredienteFactory.getIngrediente("PaoChatbotFinal", 120, false);
        IngredienteFactory.getIngrediente("CarneChatbotFinal", 280, false);
        IngredienteFactory.getIngrediente("BaconChatbotFinal", 150, false);
        ChatbotPedido chatbot = new ChatbotPedido();
        PedidoBuilder builder = new PedidoBuilder()
                .setCodigoPedido("PED-CHATBOT-001")
                .setNomeCliente("Cliente Chatbot")
                .setEstrategiaFrete(new FreteFixo());
        chatbot.adicionarAoPedido(builder, "X-Chatbot Final", 26.90f,
                "PaoChatbotFinal + CarneChatbotFinal + BaconChatbotFinal");
        Pedido pedido = builder.build();
        ItemCardapio item = pedido.getItens().get(0);
        assertEquals(1, pedido.getItens().size());
        assertEquals("X-Chatbot Final", item.getDescricao());
        assertEquals(550, item.getCalorias());
    }
}
