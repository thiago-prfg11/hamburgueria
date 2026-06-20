package test.configuracao;

import main.configuracao.ConfiguracaoHamburgueria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguracaoHamburgueriaTest {

    @Test
    void deveRetornarMesmaInstancia() {
        ConfiguracaoHamburgueria instancia1 = ConfiguracaoHamburgueria.getInstance();
        ConfiguracaoHamburgueria instancia2 = ConfiguracaoHamburgueria.getInstance();
        assertSame(instancia1, instancia2);
    }

    @Test
    void deveRetornarNomeLoja() {
        ConfiguracaoHamburgueria.getInstance().setNomeLoja("Hamburgueria do Tião");
        assertEquals("Hamburgueria do Tião", ConfiguracaoHamburgueria.getInstance().getNomeLoja());
    }

    @Test
    void deveRetornarExcecaoParaNomeLojaNulo() {
        try {
            ConfiguracaoHamburgueria.getInstance().setNomeLoja(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O nome da loja inserido é inválido!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaNomeLojaVazio() {
        try {
            ConfiguracaoHamburgueria.getInstance().setNomeLoja("   ");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O nome da loja inserido é inválido!", e.getMessage());
        }
    }

    @Test
    void deveRetornarTaxaEntregaPadrao() {
        ConfiguracaoHamburgueria.getInstance().setTaxaEntregaPadrao(5.5f);
        assertEquals(5.5f, ConfiguracaoHamburgueria.getInstance().getTaxaEntregaPadrao());
    }

    @Test
    void deveRetornarExcecaoParaTaxaEntregaNegativa() {
        try {
            ConfiguracaoHamburgueria.getInstance().setTaxaEntregaPadrao(-1f);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("A taxa de entrega inserida é inválida!", e.getMessage());
        }
    }

    @Test
    void deveRetornarHorarioAbertura() {
        ConfiguracaoHamburgueria.getInstance().setHorarioAbertura("18:00");
        assertEquals("18:00", ConfiguracaoHamburgueria.getInstance().getHorarioAbertura());
    }

    @Test
    void deveRetornarExcecaoParaHorarioAberturaNulo() {
        try {
            ConfiguracaoHamburgueria.getInstance().setHorarioAbertura(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O horário de abertura inserido é inválido!", e.getMessage());
        }
    }

    @Test
    void deveRetornarHorarioFechamento() {
        ConfiguracaoHamburgueria.getInstance().setHorarioFechamento("23:00");
        assertEquals("23:00", ConfiguracaoHamburgueria.getInstance().getHorarioFechamento());
    }

    @Test
    void deveRetornarExcecaoParaHorarioFechamentoVazio() {
        try {
            ConfiguracaoHamburgueria.getInstance().setHorarioFechamento("");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O horário de fechamento inserido é inválido!", e.getMessage());
        }
    }

    @Test
    void deveRetornarOperadorLogado() {
        ConfiguracaoHamburgueria.getInstance().setOperadorLogado("Operador 1");
        assertEquals("Operador 1", ConfiguracaoHamburgueria.getInstance().getOperadorLogado());
    }

    @Test
    void deveRetornarExcecaoParaOperadorLogadoNulo() {
        try {
            ConfiguracaoHamburgueria.getInstance().setOperadorLogado(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O operador inserido é inválido!", e.getMessage());
        }
    }

    @Test
    void deveRetornarAlcadaMaximaAtendente() {
        ConfiguracaoHamburgueria.getInstance().setAlcadaMaximaAtendente(50.0f);
        assertEquals(50.0f, ConfiguracaoHamburgueria.getInstance().getAlcadaMaximaAtendente());
    }

    @Test
    void deveRetornarExcecaoParaAlcadaMaximaNegativa() {
        try {
            ConfiguracaoHamburgueria.getInstance().setAlcadaMaximaAtendente(-10.0f);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("A alçada máxima inserida é inválida!", e.getMessage());
        }
    }
}
