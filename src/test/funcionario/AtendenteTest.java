package test.funcionario;

import main.funcionario.Atendente;
import main.funcionario.RegimeCLT;
import main.funcionario.RegimePJ;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AtendenteTest {

    @Test
    void deveRetornarSalarioComRegimeCLT() {
        Atendente atendente = new Atendente(15.0f);
        atendente.setHorasTrabalhadas(160);
        atendente.setRegimeContratacao(new RegimeCLT());
        assertEquals(4320.0f, atendente.calcularSalario(), 0.01f);
    }

    @Test
    void deveRetornarSalarioComRegimePJ() {
        Atendente atendente = new Atendente(15.0f);
        atendente.setHorasTrabalhadas(160);
        atendente.setRegimeContratacao(new RegimePJ());
        assertEquals(2400.0f, atendente.calcularSalario(), 0.01f);
    }

    @Test
    void deveRetornarExcecaoParaHorasTrabalhadasNegativas() {
        Atendente atendente = new Atendente(15.0f);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> atendente.setHorasTrabalhadas(-10));
        assertEquals("ERR03 - O número de horas trabalhadas do colaborador não pode ser negativo!", e.getMessage());
    }
}
