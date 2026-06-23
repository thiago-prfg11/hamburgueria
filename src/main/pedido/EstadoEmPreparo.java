package main.pedido;

public class EstadoEmPreparo extends EstadoPedido {

    private EstadoEmPreparo() {}
    private static final EstadoEmPreparo instance = new EstadoEmPreparo();
    public static EstadoEmPreparo getInstance() {
        return instance;
    }

    public String getDescricaoEstado() {
        return "Em Preparo";
    }

    public boolean finalizarPreparo(Pedido pedido) {
        pedido.setEstado(EstadoPronto.getInstance());
        return true;
    }

    public boolean cancelar(Pedido pedido) {
        pedido.setEstado(EstadoCancelado.getInstance());
        return true;
    }
}
