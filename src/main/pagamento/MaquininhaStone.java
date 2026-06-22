package main.pagamento;

public class MaquininhaStone extends MaquininhaBase {

    public MaquininhaStone() {
        super(1, 50000);
    }

    public String processar(int valorTransacao) {
        if (!valorValido(valorTransacao)) {
            return "ERRO";
        }
        return aprovado(valorTransacao) ? "APROVADO" : "RECUSADO";
    }
}
