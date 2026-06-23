package main.atendimento;

import main.cardapio.Lanche;
import main.pedido.PedidoBuilder;

public class ChatbotPedido {

    public Lanche montarLanche(String descricao, float preco, String expressao) {
        InterpretadorExpressaoPedido interpretador = new InterpretadorPedidoTexto(expressao);
        return new Lanche(descricao, preco, interpretador.interpretar());
    }

    public PedidoBuilder adicionarAoPedido(PedidoBuilder pedidoBuilder, String descricao, float preco, String expressao)
    {
        if (pedidoBuilder == null) {
            throw new IllegalArgumentException("ERR01 - O builder referenciado não pode ser nulo!");
        }
        Lanche lanche = montarLanche(descricao, preco, expressao);
        return pedidoBuilder.addItem(lanche);
    }
}
