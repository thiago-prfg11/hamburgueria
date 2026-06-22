package main.pedido;

import main.atendimento.IObserver;

public class AppClienteObserver implements IObserver {

    private final String nomeCliente;
    private String ultimaNotificacao;

    public AppClienteObserver(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo ou vazio!");
        }
        this.nomeCliente = nomeCliente;
    }

    public void notificar(Pedido pedido) {
        this.ultimaNotificacao = "Atenção, " + this.nomeCliente + ", seu pedido " + pedido.getCodigoPedido() +
                " se encontra no seguinte estado: " + pedido.getDescricaoEstado();
    }

    public String getUltimaNotificacao() {
        return ultimaNotificacao;
    }
}
