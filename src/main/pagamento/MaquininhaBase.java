package main.pagamento;

public abstract class MaquininhaBase {

    protected int valorMinimo;
    protected int valorMaximo;

    public MaquininhaBase(int valorMinimo, int valorMaximo) {
        if (valorMinimo <= 0) {
            throw new IllegalArgumentException("ERR03 - O valor mínimo da transação não pode ser menor ou igual a zero!");
        }
        if (valorMaximo <= 0) {
            throw new IllegalArgumentException("ERR03 - O valor máximo da transação não pode ser menor ou igual a zero!");
        }
        if (valorMinimo > valorMaximo) {
            throw new IllegalArgumentException("ERR10 - O valor mínimo da transação não pode ser maior que o máximo!");
        }
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
    }

    protected boolean valorValido(int valorTransacao) {
        return valorTransacao > 0;
    }

    protected boolean aprovado(int valorTransacao) {
        return valorTransacao >= valorMinimo && valorTransacao <= valorMaximo;
    }
}
