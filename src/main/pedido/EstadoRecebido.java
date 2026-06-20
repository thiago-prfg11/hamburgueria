package main.pedido;

public class EstadoRecebido extends EstadoPedido {

    private EstadoRecebido() {};
    private static final EstadoRecebido instance = new EstadoRecebido();
    public static EstadoRecebido getInstance() {
        return instance;
    }

    public String getDescricaoEstado() {
        return "Recebido";
    }

    public boolean confirmarPreparo(Pedido pedido) {
        pedido.setEstado(EstadoEmPreparo.getInstance());
        return true;
    }

    public boolean cancelar(Pedido pedido) {
        pedido.setEstado(EstadoCancelado.getInstance());
        return true;
    }
}
