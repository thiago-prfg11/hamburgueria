package main.pedido;

public class EstadoSaiuParaEntrega extends EstadoPedido {

    private EstadoSaiuParaEntrega() {};
    private static final EstadoSaiuParaEntrega instance = new EstadoSaiuParaEntrega();
    public static EstadoSaiuParaEntrega getInstance() {
        return instance;
    }

    public String getDescricaoEstado() {
        return "Saiu para Entrega";
    }

    public boolean entregar(Pedido pedido) {
        pedido.setEstado(EstadoEntregue.getInstance());
        return true;
    }
}
