package main.pagamento;

public class AdaptadorGertec implements IProcessadorPagamento {

    private final MaquininhaGertec maquininhaGertec = new MaquininhaGertec();

    public StatusPagamento processar(int valorTransacao) {
        int codigoRetorno = this.maquininhaGertec.processar(valorTransacao);
        if (codigoRetorno == 0) {
            return StatusPagamento.APROVADO;
        }
        if (codigoRetorno == 1) {
            return StatusPagamento.RECUSADO;
        }
        return StatusPagamento.ERRO;
    }
}



