package main.pedido;

import main.cardapio.ItemCardapio;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Pedido extends Observable {

    private final String codigoPedido;
    private String nomeCliente;
    private String enderecoEntrega;
    private String formaPagamento;
    private float distanciaKm;
    private float valorFrete;
    private float percentualDesconto;
    private final List<ItemCardapio> itens;
    private EstadoPedido estado;

    public Pedido(String codigoPedido) {
        this(codigoPedido, new ArrayList<>());
    }

    public Pedido(String codigoPedido, List<ItemCardapio> itens) {
        if (codigoPedido == null || codigoPedido.isBlank()) {
            throw new IllegalArgumentException("ERR02 - O código do pedido não pode ser nulo ou vazio!");
        }
        if (itens == null) {
            throw new IllegalArgumentException("ERR01 - A lista de itens referenciada não pode ser nula!");
        }
        this.codigoPedido = codigoPedido;
        this.itens = new ArrayList<>(itens);
        this.estado = EstadoRecebido.getInstance();
        this.percentualDesconto = 0;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
        setChanged();
        notifyObservers();
    }

    public boolean confirmarPreparo() {
        return estado.confirmarPreparo(this);
    }

    public boolean finalizarPreparo() {
        return estado.finalizarPreparo(this);
    }

    public boolean despachar() {
        return estado.despachar(this);
    }

    public boolean entregar() {
        return estado.entregar(this);
    }

    public boolean cancelar() {
        return estado.cancelar(this);
    }

    public String getDescricaoEstado() {
        return estado.getDescricaoEstado();
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("ERR02 - O nome do cliente não pode ser nulo ou vazio!");
        }
        this.nomeCliente = nomeCliente;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public float getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(float distanciaKm) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("ERR03 - A distância de entrega não pode ser negativa!");
        }
        this.distanciaKm = distanciaKm;
    }

    public float getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(float valorFrete) {
        if (valorFrete < 0) {
            throw new IllegalArgumentException("ERR03 - O valor do frete não pode ser negativo!");
        }
        this.valorFrete = valorFrete;
    }

    public void aplicarDesconto(float percentual) {
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("ERR04 - O percentual de desconto não pode ser" +
                    " negativo ou maior que 100!");
        }
        this.percentualDesconto = percentual;
    }

    public float getPercentualDesconto() {
        return percentualDesconto;
    }

    public List<ItemCardapio> getItens() {
        return new ArrayList<>(this.itens);
    }

    public float getValorItens() {
        float total = 0;
        for (ItemCardapio item : this.itens) {
            total += item.getPreco();
        }
        return total;
    }

    public float getValorTotal() {
        float totalItens = getValorItens();
        float totalComDesconto = totalItens * (1 - this.percentualDesconto / 100);
        return totalComDesconto + this.valorFrete;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "Código:'" + codigoPedido + '\'' +
                ", Nome do Cliente:'" + nomeCliente + '\'' +
                ", Estado Atual:'" + estado.getDescricaoEstado() + '\'' +
                ", Itens:" + itens.size() +
                ", Valor Total:" + getValorTotal() +
                '}';
    }
}