package main.operacional;

public abstract class AprovadorDesconto {

    private final float limiteDesconto;
    private AprovadorDesconto superior;

    public AprovadorDesconto(float limiteDesconto) {
        if (limiteDesconto < 0 || limiteDesconto > 100) {
            throw new IllegalArgumentException("O limite de desconto não pode ser negativo ou maior que 100!");
        }
        this.limiteDesconto = limiteDesconto;
    }

    public void setSuperior(AprovadorDesconto superior) {
        this.superior = superior;
    }

    public String aprovarDesconto(SolicitacaoDesconto solicitacao) {
        if (solicitacao.getPercentualDesconto() <= this.limiteDesconto) {
            solicitacao.getPedido().aplicarDesconto(solicitacao.getPercentualDesconto());
            return getDescricaoCargo();
        }
        if (this.superior != null) {
            return this.superior.aprovarDesconto(solicitacao);
        }
        return "Pedido de Desconto Negado!";
    }

    public abstract String getDescricaoCargo();
}
