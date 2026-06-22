package main.pagamento;

public class MaquininhaCielo extends MaquininhaBase {

    public MaquininhaCielo() {
        super(1, 30000);
    }

    public boolean processar(int valorTransacao) {
        if (!valorValido(valorTransacao)) {
            throw new IllegalArgumentException("O valor de transação referenciado é inválido!");
        }
        return aprovado(valorTransacao);
    }
}
