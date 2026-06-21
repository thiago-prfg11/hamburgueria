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
    private final List<ItemCardapio> itens;
    private EstadoPedido estado;

    public Pedido(String codigoPedido) {
        this(codigoPedido, new ArrayList<>());
    }

    public Pedido(String codigoPedido, List<ItemCardapio> itens) {
        if (codigoPedido == null || codigoPedido.isBlank()) {
            throw new IllegalArgumentException("O código do pedido não pode ser nulo ou vazio!");
        }
        if (itens == null) {
            throw new IllegalArgumentException("A lista de itens do pedido não pode ser nula!");
        }
        this.codigoPedido = codigoPedido;
        this.itens = new ArrayList<>(itens);
        this.estado = EstadoRecebido.getInstance();
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

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo ou vazio!");
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
            throw new IllegalArgumentException("A distância não pode ser negativa!");
        }
        this.distanciaKm = distanciaKm;
    }

    public float getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(float valorFrete) {
        if (valorFrete < 0) {
            throw new IllegalArgumentException("O valor do frete inserido não pode ser negativo!");
        }
        this.valorFrete = valorFrete;
    }

    public List<ItemCardapio> getItens() {
        return new ArrayList<>(this.itens);
    }

    public float getValorTotal() {
        float total = this.valorFrete;
        for (ItemCardapio item : this.itens) {
            total += item.getPreco();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "Código:'" + codigoPedido + '\'' +
                ", Nome do Cliente:'" + nomeCliente + '\'' +
                ", Estado Atual:='" + estado.getDescricaoEstado() + '\'' +
                ", Itens Inclusos:" + itens.size() +
                ", Valor Total:" + getValorTotal() +
                '}';
    }
}