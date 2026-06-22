package main.pagamento;

public abstract class MaquininhaBase {

    protected int valorMinimo;
    protected int valorMaximo;

    public MaquininhaBase(int valorMinimo, int valorMaximo) {
        if (valorMinimo <= 0) {
            throw new IllegalArgumentException("O valor mínimo não pode ser negativo!");
        }
        if (valorMaximo <= 0) {
            throw new IllegalArgumentException("O valor máximo não pode ser negativo!");
        }
        if (valorMinimo > valorMaximo) {
            throw new IllegalArgumentException("Valor mínimo não pode ser maior que o máximo!");
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
