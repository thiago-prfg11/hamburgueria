package main.pagamento;

public interface IProcessadorPagamento {

    StatusPagamento processar(int valorTransacao);
}
