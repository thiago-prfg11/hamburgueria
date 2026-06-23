package main.atendimento;

import main.pedido.Pedido;

public interface IServicoPedido {

    String iniciar(Pedido pedido);
    String cancelarDespacho();
}
