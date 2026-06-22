package main.pagamento;

public class AdaptadorCielo implements IProcessadorPagamento {

    private final MaquininhaCielo maquininhaCielo = new MaquininhaCielo();

    public StatusPagamento processar(int valorTransacao) {
        try {
            boolean aprovado = this.maquininhaCielo.processar(valorTransacao);
            return aprovado ? StatusPagamento.APROVADO : StatusPagamento.RECUSADO;
        } catch (IllegalArgumentException e) {
            return StatusPagamento.ERRO;
        }
    }
}
