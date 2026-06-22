package main.atendimento;

import main.pedido.Pedido;

public interface IObserver {

    void notificar(Pedido pedido);
}
