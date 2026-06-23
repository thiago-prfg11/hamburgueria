package main.atendimento;

import main.pedido.Pedido;

public class ServicoDelivery implements IServicoPedido {

    public String iniciar(Pedido pedido) {
        pedido.despachar();
        EmbaladorPedido embalador = new EmbaladorPedido(new FabricaEmbalagemDelivery());
        return "Pedido aguardando retirada no balcão pelo entregador, embalado em "
                + embalador.getDescricaoEmbalagem() + " com " + embalador.getDescricaoProtecao();
    }

    public String cancelarDespacho() {
        return "Retirada do pedido pelo entregador cancelada";
    }
}
