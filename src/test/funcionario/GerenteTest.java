package test.funcionario;

import main.funcionario.Gerente;
import main.funcionario.RegimeCLT;
import main.funcionario.RegimePJ;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GerenteTest {

    @Test
    void deveRetornarSalarioComRegimeCLT() {
        Gerente gerente = new Gerente(5000.0f);
        gerente.setBonusDesempenho(1000.0f);
        gerente.setRegimeContratacao(new RegimeCLT());

        assertEquals(10800.0f, gerente.calcularSalario(), 0.01f);
    }

    @Test
    void deveRetornarSalarioComRegimePJ() {
        Gerente gerente = new Gerente(5000.0f);
        gerente.setBonusDesempenho(1000.0f);
        gerente.setRegimeContratacao(new RegimePJ());

        assertEquals(6000.0f, gerente.calcularSalario(), 0.01f);
    }

    @Test
    void deveRetornarExcecaoParaBonusDesempenhoNegativo() {
        Gerente gerente = new Gerente(5000.0f);
        try {
            gerente.setBonusDesempenho(-200.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O bônus por desempenho não pode ser negativo!", e.getMessage());
        }
    }
}
