package main.funcionario;

public abstract class Cargo {

    protected RegimeContratacao regimeContratacao;
    protected float salarioBase;

    public Cargo(float salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("O salário base do colaborador não pode ser negativo!");
        }
        this.salarioBase = salarioBase;
    }

    public void setRegimeContratacao(RegimeContratacao regimeContratacao) {
        if (regimeContratacao == null) {
            throw new IllegalArgumentException("O regime de contratação do colaborador não pode ser nulo!");
        }
        this.regimeContratacao = regimeContratacao;
    }

    public void setSalarioBase(float salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("O salário base do colaborador não pode ser negativo!");
        }
        this.salarioBase = salarioBase;
    }

    protected void validarRegimeDefinido() {
        if (this.regimeContratacao == null) {
            throw new IllegalStateException("O regime de contratação do colaborador não pode ser nulo!");
        }
    }

    public abstract float calcularSalario();
}
