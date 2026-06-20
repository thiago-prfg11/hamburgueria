package main.pedido;

public class Pedido {

    private final String codigoPedido;
    private EstadoPedido estado;

    public Pedido(String codigoPedido) {
        if (codigoPedido == null || codigoPedido.isBlank()) {
            throw new IllegalArgumentException("O código do pedido não pode ser nulo ou vazio!");
        }
        this.codigoPedido = codigoPedido;
        this.estado = EstadoRecebido.getInstance();
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
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
}
