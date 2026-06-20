package test.funcionario;

import main.funcionario.RegimeCLT;
import main.funcionario.RegimePJ;
import main.funcionario.Supervisor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupervisorTest {

    @Test
    void deveRetornarSalarioComRegimeCLT() {
        Supervisor supervisor = new Supervisor(3000.0f);
        supervisor.setRegimeContratacao(new RegimeCLT());
        assertEquals(5400.0f, supervisor.calcularSalario(), 0.01f);
    }

    @Test
    void deveRetornarSalarioComRegimePJ() {
        Supervisor supervisor = new Supervisor(3000.0f);
        supervisor.setRegimeContratacao(new RegimePJ());
        assertEquals(3000.0f, supervisor.calcularSalario(), 0.01f);
    }

    @Test
    void deveRetornarExcecaoParaSalarioBaseNegativoNoConstrutor() {
        try {
            new Supervisor(-100.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O salário base do colaborador não pode ser negativo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaSalarioBaseNegativoNoSetter() {
        Supervisor supervisor = new Supervisor(3000.0f);
        try {
            supervisor.setSalarioBase(-50.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O salário base do colaborador não pode ser negativo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaRegimeContratacaoNulo() {
        Supervisor supervisor = new Supervisor(3000.0f);
        try {
            supervisor.setRegimeContratacao(null);
        } catch (IllegalArgumentException e) {
            assertEquals("O regime de contratação do colaborador não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCalcularSalarioSemRegimeDefinido() {
        Supervisor supervisor = new Supervisor(3000.0f);
        try {
            supervisor.calcularSalario();
        } catch (IllegalStateException e) {
            assertEquals("O regime de contratação do colaborador não pode ser nulo!", e.getMessage());
        }
    }
}
