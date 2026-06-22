package main.atendimento;

import main.pedido.Pedido;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class CentralAtendimento implements Observer {

    private static final CentralAtendimento instancia = new CentralAtendimento();
    private final Map<String, List<IObserver>> observersPorPedido;
    private final Map<String, List<String>> historicoPorPedido;

    private CentralAtendimento() {
        this.observersPorPedido = new HashMap<String, List<IObserver>>();
        this.historicoPorPedido = new HashMap<String, List<String>>();
    }

    public static CentralAtendimento getInstance() {
        return instancia;
    }

    public void acompanharPedido(Pedido pedido, IObserver observer) {
        if (pedido == null) {
            throw new IllegalArgumentException("O pedido referenciado não pode ser nulo!");
        }
        if (observer == null) {
            throw new IllegalArgumentException("O observador referenciado não pode ser nulo!");
        }
        pedido.addObserver(this);
        String codigo = pedido.getCodigoPedido();
        if (!this.observersPorPedido.containsKey(codigo)) {
            this.observersPorPedido.put(codigo, new ArrayList<IObserver>());
        }
        this.observersPorPedido.get(codigo).add(observer);
    }

    public void update(Observable observable, Object arg) {
        if (!(observable instanceof Pedido)) {
            return;
        }
        Pedido pedido = (Pedido) observable;
        String codigo = pedido.getCodigoPedido();
        String evento = "Pedido " + codigo + " está: " + pedido.getDescricaoEstado();
        if (!this.historicoPorPedido.containsKey(codigo)) {
            this.historicoPorPedido.put(codigo, new ArrayList<String>());
        }
        this.historicoPorPedido.get(codigo).add(evento);
        List<IObserver> observers = this.observersPorPedido.get(codigo);
        if (observers != null) {
            for (IObserver observer : observers) {
                observer.notificar(pedido);
            }
        }
    }

    public List<String> getHistoricoPedido(String codigoPedido) {
        if (codigoPedido == null || codigoPedido.isBlank()) {
            throw new IllegalArgumentException("O código do pedido referenciado não pode ser nulo ou vazio!");
        }
        List<String> historico = this.historicoPorPedido.get(codigoPedido);
        if (historico == null) {
            return new ArrayList<String>();
        }
        return new ArrayList<String>(historico);
    }

    public void limpar() {
        this.observersPorPedido.clear();
        this.historicoPorPedido.clear();
    }
}