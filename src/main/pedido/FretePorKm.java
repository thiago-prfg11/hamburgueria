package main.pedido;

public class FretePorKm implements EstrategiaFrete {

    private final float valorPorKm;

    public FretePorKm(float valorPorKm) {
        if (valorPorKm < 0) {
            throw new IllegalArgumentException("O valor do quilômetro rodado não pode ser negativo!");
        }
        this.valorPorKm = valorPorKm;
    }

    public float calcularFrete(float distanciaKm, float valorPedido) {
        return this.valorPorKm * distanciaKm;
    }
}
