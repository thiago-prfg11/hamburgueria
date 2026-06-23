package main.pedido;

public class EstadoPronto extends EstadoPedido {

    private EstadoPronto() {};
    private static final EstadoPronto instance = new EstadoPronto();
    public static EstadoPronto getInstance() {
        return instance;
    }

    public String getDescricaoEstado() {
        return "Pronto";
    }

    public boolean despachar(Pedido pedido) {
        pedido.setEstado(EstadoSaiuParaEntrega.getInstance());
        return true;
    }

    public boolean entregar(Pedido pedido) {
        pedido.setEstado(EstadoEntregue.getInstance());
        return true;
    }

    public boolean cancelar(Pedido pedido) {
        pedido.setEstado(EstadoCancelado.getInstance());
        return true;
    }
}
