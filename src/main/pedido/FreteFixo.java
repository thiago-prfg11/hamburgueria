package main.pedido;

import main.configuracao.ConfiguracaoHamburgueria;

public class FreteFixo implements EstrategiaFrete {

    public float calcularFrete(float distanciaKm, float valorPedido) {
        return ConfiguracaoHamburgueria.getInstance().getTaxaEntregaPadrao();
    }
}
