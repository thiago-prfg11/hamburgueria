package main.atendimento;


import main.pedido.Pedido;

public class ServicoRetirada implements IServicoPedido {

    public String iniciar(Pedido pedido) {
        pedido.entregar();
        EmbaladorPedido embalador = new EmbaladorPedido(new FabricaEmbalagemBalcao());
        return "O pedido está aguardando retirada no balcão pelo cliente, embalado em "
                + embalador.getDescricaoEmbalagem() + " com " + embalador.getDescricaoProtecao();
    }

    public String cancelarDespacho() {
        return "A retirada do pedido pelo cliente foi cancelada!";
    }
}
