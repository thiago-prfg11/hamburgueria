package main.funcionario;

public class Supervisor extends Cargo {

    public Supervisor(float salarioBase) {
        super(salarioBase);
    }

    public float calcularSalario() {
        validarRegimeDefinido();
        return this.salarioBase * (1 + this.regimeContratacao.percentualEncargos());
    }
}
