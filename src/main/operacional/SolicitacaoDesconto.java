package main.operacional;

import main.pedido.Pedido;

public class SolicitacaoDesconto {

    private final Pedido pedido;
    private final float percentualDesconto;

    public SolicitacaoDesconto(Pedido pedido, float percentualDesconto) {
        if (pedido == null) {
            throw new IllegalArgumentException("O pedido referenciado não pode ser nulo!");
        }
        if (percentualDesconto < 0 || percentualDesconto > 100) {
            throw new IllegalArgumentException("O percentual de desconto não pode ser negativo ou maior que 100!");
        }
        this.pedido = pedido;
        this.percentualDesconto = percentualDesconto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public float getPercentualDesconto() {
        return percentualDesconto;
    }
}
