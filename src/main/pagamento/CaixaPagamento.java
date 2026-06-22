package main.pagamento;

public class CaixaPagamento {

    private final IProcessadorPagamento processadorPagamento;

    public CaixaPagamento(IProcessadorPagamento processadorPagamento) {
        if (processadorPagamento == null) {
            throw new IllegalArgumentException("O processador de pagamento referenciado é inválido!");
        }
        this.processadorPagamento = processadorPagamento;
    }

    public StatusPagamento processarPagamento(int valorTransacao) {
        return this.processadorPagamento.processar(valorTransacao);
    }
}
