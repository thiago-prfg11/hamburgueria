package main.atendimento;


import main.pedido.Pedido;

public class ServicoRetirada implements IServicoPedido {

    public String iniciar(Pedido pedido) {
        pedido.entregar();
        EmbaladorPedido embalador = new EmbaladorPedido(new FabricaEmbalagemBalcao());
        return "Pedido aguardando retirada no balcão pelo cliente, embalado em "
                + embalador.getDescricaoEmbalagem() + " com " + embalador.getDescricaoProtecao();
    }

    public String cancelarDespacho() {
        return "Retirada do pedido pelo cliente cancelada";
    }
}
