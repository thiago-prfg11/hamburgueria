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
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Supervisor(-100.0f));
        assertEquals("ERR03 - O salário base do colaborador não pode ser negativo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaSalarioBaseNegativoNoSetter() {
        Supervisor supervisor = new Supervisor(3000.0f);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> supervisor.setSalarioBase(-50.0f));
        assertEquals("ERR03 - O salário base do colaborador não pode ser negativo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaRegimeContratacaoNulo() {
        Supervisor supervisor = new Supervisor(3000.0f);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> supervisor.setRegimeContratacao(null));
        assertEquals("ERR01 - O regime de contratação do colaborador não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCalcularSalarioSemRegimeDefinido() {
        Supervisor supervisor = new Supervisor(3000.0f);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> supervisor.calcularSalario());
        assertEquals("ERR01 - O regime de contratação do colaborador não pode ser nulo!", e.getMessage());
    }
}
