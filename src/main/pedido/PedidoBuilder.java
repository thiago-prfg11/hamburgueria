package main.pedido;

import main.cardapio.ItemCardapio;

import java.util.ArrayList;
import java.util.List;

public class PedidoBuilder {

    private String codigoPedido;
    private String nomeCliente;
    private String enderecoEntrega;
    private String formaPagamento;
    private final List<ItemCardapio> itens;
    private EstrategiaFrete estrategiaFrete;
    private float distanciaKm;

    public PedidoBuilder() {
        this.itens = new ArrayList<>();
    }

    public PedidoBuilder setCodigoPedido(String codigoPedido) {
        if (codigoPedido == null || codigoPedido.isBlank()) {
            throw new IllegalArgumentException("O código do pedido não pode ser nulo ou vazio!");
        }
        this.codigoPedido = codigoPedido;
        return this;
    }

    public PedidoBuilder setNomeCliente(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo ou vazio!");
        }
        this.nomeCliente = nomeCliente;
        return this;
    }

    public PedidoBuilder setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
        return this;
    }

    public PedidoBuilder setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
        return this;
    }

    public PedidoBuilder setEstrategiaFrete(EstrategiaFrete estrategiaFrete) {
        if (estrategiaFrete == null) {
            throw new IllegalArgumentException("A estratégia de frete não pode ser nula!");
        }
        this.estrategiaFrete = estrategiaFrete;
        return this;
    }

    public PedidoBuilder setDistanciaKm(float distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("A distância para entrega não pode ser negativa!");
        }
        this.distanciaKm = distanciaKm;
        return this;
    }

    public PedidoBuilder addItem(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("O item inserido não pode ser nulo!");
        }
        this.itens.add(item);
        return this;
    }

    public PedidoBuilder removerItem(ItemCardapio item) {
        this.itens.remove(item);
        return this;
    }

    public List<ItemCardapio> getItensAtuais() {
        return new ArrayList<>(this.itens);
    }

    public Pedido build() {
        if (codigoPedido == null) {
            throw new IllegalArgumentException("O código do pedido não pode ser nulo!");
        }
        if (nomeCliente == null) {
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo!");
        }
        if (itens.isEmpty()) {
            throw new IllegalArgumentException("O pedido deve ter ao menos um item!");
        }
        if (estrategiaFrete == null) {
            throw new IllegalArgumentException("A estratégia de frete não pode ser nula!");
        }

        float valorItens = 0;
        for (ItemCardapio item : itens) {
            valorItens += item.getPreco();
        }
        float valorFrete = new CalculadoraFrete(distanciaKm, valorItens).calcularFrete(estrategiaFrete);

        Pedido pedido = new Pedido(codigoPedido, itens);
        pedido.setNomeCliente(nomeCliente);
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setDistanciaKm(distanciaKm);
        pedido.setValorFrete(valorFrete);
        return pedido;
    }
}
