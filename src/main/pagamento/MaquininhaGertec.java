package main.pagamento;

public class MaquininhaGertec extends MaquininhaBase {

    public MaquininhaGertec() {
        super(1, 15000);
    }

    public int processar(int valorTransacao) {
        if (!valorValido(valorTransacao)) {
            return -1;
        }
        return aprovado(valorTransacao) ? 0 : 1;
    }
}
