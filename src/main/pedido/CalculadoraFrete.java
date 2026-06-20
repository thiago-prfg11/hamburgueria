package main.pedido;

public class CalculadoraFrete {

    private final float distanciaKm;
    private final float valorPedido;

    public CalculadoraFrete(float distanciaKm, float valorPedido) {
        if (distanciaKm < 0) {
            throw new IllegalArgumentException("A distância para entrega não pode ser negativa!");
        }
        if (valorPedido < 0) {
            throw new IllegalArgumentException("O valor do pedido não pode ser negativo!");
        }
        this.distanciaKm = distanciaKm;
        this.valorPedido = valorPedido;
    }

    public float calcularFrete(EstrategiaFrete estrategia) {
        if (estrategia == null) {
            throw new IllegalArgumentException("A estratégia de entrega informada não pode ser nula!");
        }
        return estrategia.calcularFrete(this.distanciaKm, this.valorPedido);
    }
}
