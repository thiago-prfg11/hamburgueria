package main.pedido;

public class EstadoEntregue extends EstadoPedido {

    private EstadoEntregue() {};
    private static final EstadoEntregue instance = new EstadoEntregue();
    public static EstadoEntregue getInstance() {
        return instance;
    }

    public String getDescricaoEstado() {
        return "Entregue";
    }
}
