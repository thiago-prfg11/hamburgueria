package main.operacional;

import main.pedido.Pedido;

public record SolicitacaoDesconto(Pedido pedido, float percentualDesconto) {

    public SolicitacaoDesconto {
        if (pedido == null) {
            throw new IllegalArgumentException("ERR01 - O pedido referenciado não pode ser nulo!");
        }
        if (percentualDesconto < 0 || percentualDesconto > 100) {
            throw new IllegalArgumentException("ERR04 - O percentual de desconto não pode ser negativo" +
                    " ou maior que 100!");
        }
    }
}
