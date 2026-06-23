package main.funcionario;

public class Atendente extends Cargo {

    private int horasTrabalhadas;

    public Atendente(float salarioBase) {
        super(salarioBase);
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        if (horasTrabalhadas < 0) {
            throw new IllegalArgumentException("ERR03 - O número de horas trabalhadas do colaborador não pode" +
                    " ser negativo!");
        }
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public float calcularSalario() {
        validarRegimeDefinido();
        return this.salarioBase * this.horasTrabalhadas * (1 + this.regimeContratacao.percentualEncargos());
    }
}
