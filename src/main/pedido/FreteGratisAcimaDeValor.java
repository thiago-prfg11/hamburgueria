package main.pedido;

public class FreteGratisAcimaDeValor implements EstrategiaFrete {

    private final float valorMinimoFreteGratis;
    private final float valorFretePadrao;

    public FreteGratisAcimaDeValor(float valorMinimoFreteGratis, float valorFretePadrao) {
        if (valorMinimoFreteGratis < 0) {
            throw new IllegalArgumentException("O valor mínimo para o frete grátis não pode ser negativo!");
        }
        if (valorFretePadrao < 0) {
            throw new IllegalArgumentException("O valor padrão do frete não pode ser negativo!");
        }
        this.valorMinimoFreteGratis = valorMinimoFreteGratis;
        this.valorFretePadrao = valorFretePadrao;
    }

    public float calcularFrete(float distanciaKm, float valorPedido) {
        if (valorPedido >= this.valorMinimoFreteGratis) {
            return 0f;
        }
        return this.valorFretePadrao;
    }
}
