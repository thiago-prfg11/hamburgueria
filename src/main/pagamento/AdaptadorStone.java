package main.pagamento;

public class AdaptadorStone implements IProcessadorPagamento {

    private final MaquininhaStone maquininhaStone = new MaquininhaStone();

    public StatusPagamento processar(int valorTransacao) {
        String retorno = this.maquininhaStone.processar(valorTransacao);
        if (retorno.equals("APROVADO")) {
            return StatusPagamento.APROVADO;
        }
        if (retorno.equals("RECUSADO")) {
            return StatusPagamento.RECUSADO;
        }
        return StatusPagamento.ERRO;
    }
}
