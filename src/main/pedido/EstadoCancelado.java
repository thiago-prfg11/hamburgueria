package main.pedido;

public class EstadoCancelado extends EstadoPedido {

    private EstadoCancelado() {};
    private static final EstadoCancelado instance = new EstadoCancelado();
    public static EstadoCancelado getInstance() {
        return instance;
    }

    public String getDescricaoEstado() {
        return "Cancelado";
    }
}
