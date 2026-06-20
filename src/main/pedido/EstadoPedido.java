package main.pedido;

public abstract class EstadoPedido {

    public abstract String getDescricaoEstado();

    public boolean confirmarPreparo(Pedido pedido) {
        return false;
    }

    public boolean finalizarPreparo(Pedido pedido) {
        return false;
    }

    public boolean despachar(Pedido pedido) {
        return false;
    }

    public boolean entregar(Pedido pedido) {
        return false;
    }

    public boolean cancelar(Pedido pedido) {
        return false;
    }
}
