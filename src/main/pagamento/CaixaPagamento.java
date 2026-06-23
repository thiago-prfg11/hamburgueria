package main.pagamento;

public class CaixaPagamento {

    private final IProcessadorPagamento processadorPagamento;

    public CaixaPagamento(IProcessadorPagamento processadorPagamento) {
        if (processadorPagamento == null) {
            throw new IllegalArgumentException("ERR01 - O processador de pagamento referenciado não pode ser nulo!");
        }
        this.processadorPagamento = processadorPagamento;
    }

    public StatusPagamento processarPagamento(float valorReais) {
        int valorCentavos = Math.round(valorReais * 100);
        return this.processadorPagamento.processar(valorCentavos);
    }
}
