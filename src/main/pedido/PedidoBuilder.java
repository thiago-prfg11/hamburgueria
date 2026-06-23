package main.pedido;

import main.cardapio.ItemCardapio;
import java.util.ArrayList;
import java.util.List;

public class PedidoBuilder {

    private String codigoPedido;
    private String nomeCliente;
    private String enderecoEntrega;
    private String formaPagamento;
    private List<ItemCardapio> itens;
    private final List<PedidoSnapshot> historico;
    private EstrategiaFrete estrategiaFrete;
    private float distanciaKm;

    public PedidoBuilder() {
        this.itens = new ArrayList<>();
        this.historico = new ArrayList<>();
    }

    public PedidoBuilder setCodigoPedido(String codigoPedido) {
        if (codigoPedido == null || codigoPedido.isBlank()) {
            throw new IllegalArgumentException("ERR02 - O código do pedido não pode ser nulo ou vazio!");
        }
        this.codigoPedido = codigoPedido;
        return this;
    }

    public PedidoBuilder setNomeCliente(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("ERR02 - O nome do cliente não pode ser nulo ou vazio!");
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
            throw new IllegalArgumentException("ERR01 - A estratégia de frete referenciada não pode ser nula!");
        }
        this.estrategiaFrete = estrategiaFrete;
        return this;
    }

    public PedidoBuilder setDistanciaKm(float distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("ERR03 - A distância para entrega não pode ser negativa!");
        }
        this.distanciaKm = distanciaKm;
        return this;
    }

    public PedidoBuilder addItem(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("ERR01 - O item referenciado não pode ser nulo!");
        }
        this.itens.add(item);
        this.historico.add(new PedidoSnapshot(this.itens));
        return this;
    }

    public PedidoBuilder removerItem(ItemCardapio item) {
        this.itens.remove(item);
        this.historico.add(new PedidoSnapshot(this.itens));
        return this;
    }

    public PedidoBuilder restaurarParaIndice(int indice) {
        if (indice < 0 || indice > this.historico.size() - 1) {
            throw new IllegalArgumentException("ERR07 - O índice inserido para restauração não é válido!");
        }
        this.itens = new ArrayList<>(this.historico.get(indice).itens());
        return this;
    }

    public List<PedidoSnapshot> getHistorico() {
        return new ArrayList<>(this.historico);
    }

    public List<ItemCardapio> getItensAtuais() {
        return new ArrayList<>(this.itens);
    }

    public Pedido build() {
        if (codigoPedido == null) {
            throw new IllegalArgumentException("ERR01 - O código do pedido não pode ser nulo!");
        }
        if (nomeCliente == null) {
            throw new IllegalArgumentException("ERR01 - O nome do cliente não pode ser nulo!");
        }
        if (itens.isEmpty()) {
            throw new IllegalArgumentException("ERR02 - O pedido deve ter ao menos um item!");
        }
        if (estrategiaFrete == null) {
            throw new IllegalArgumentException("ERR01 - A estratégia de frete referenciada não pode ser nula!");
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
