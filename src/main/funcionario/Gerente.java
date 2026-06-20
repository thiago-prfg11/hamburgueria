package main.funcionario;

public class Gerente extends Cargo {

    private float bonusDesempenho;

    public Gerente(float salarioBase) {
        super(salarioBase);
    }

    public void setBonusDesempenho(float bonusDesempenho) {
        if (bonusDesempenho < 0) {
            throw new IllegalArgumentException("O bônus por desempenho não pode ser negativo!");
        }
        this.bonusDesempenho = bonusDesempenho;
    }

    public float getBonusDesempenho() {
        return bonusDesempenho;
    }

    public float calcularSalario() {
        validarRegimeDefinido();
        return (this.salarioBase + this.bonusDesempenho) * (1 + this.regimeContratacao.percentualEncargos());
    }
}
