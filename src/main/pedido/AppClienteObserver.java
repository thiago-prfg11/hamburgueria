package main.pedido;

import java.util.Observable;
import java.util.Observer;

public class AppClienteObserver implements Observer {

    private final String nomeCliente;
    private String ultimaNotificacao;

    public AppClienteObserver(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo ou vazio!");
        }
        this.nomeCliente = nomeCliente;
    }

    public String getUltimaNotificacao() {
        return ultimaNotificacao;
    }

    public void acompanhar(Pedido pedido) {
        pedido.addObserver(this);
    }

    public void update(Observable observable, Object arg) {
        Pedido pedido = (Pedido) observable;
        this.ultimaNotificacao = "Olá, " + this.nomeCliente + "! Seu pedido " + pedido.getCodigoPedido() +
                " se encontra no seguinte estado: " + pedido.getDescricaoEstado();
    }
}
