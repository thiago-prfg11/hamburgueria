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
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ConfiguracaoHamburgueria.getInstance().setNomeLoja(null));
        assertEquals("ERR02 - O nome da loja não pode ser nulo ou em branco!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaNomeLojaVazio() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ConfiguracaoHamburgueria.getInstance().setNomeLoja("   "));
        assertEquals("ERR02 - O nome da loja não pode ser nulo ou em branco!", e.getMessage());
    }

    @Test
    void deveRetornarTaxaEntregaPadrao() {
        ConfiguracaoHamburgueria.getInstance().setTaxaEntregaPadrao(5.5f);
        assertEquals(5.5f, ConfiguracaoHamburgueria.getInstance().getTaxaEntregaPadrao());
    }

    @Test
    void deveRetornarExcecaoParaTaxaEntregaNegativa() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ConfiguracaoHamburgueria.getInstance().setTaxaEntregaPadrao(-1f));
        assertEquals("ERR03 - A taxa de entrega não pode ser negativa!", e.getMessage());
    }

    @Test
    void deveRetornarHorarioAbertura() {
        ConfiguracaoHamburgueria.getInstance().setHorarioAbertura("18:00");
        assertEquals("18:00", ConfiguracaoHamburgueria.getInstance().getHorarioAbertura());
    }

    @Test
    void deveRetornarExcecaoParaHorarioAberturaNulo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ConfiguracaoHamburgueria.getInstance().setHorarioAbertura(null));
        assertEquals("ERR02 - O horário de abertura não pode ser nulo ou em branco!", e.getMessage());
    }

    @Test
    void deveRetornarHorarioFechamento() {
        ConfiguracaoHamburgueria.getInstance().setHorarioFechamento("23:00");
        assertEquals("23:00", ConfiguracaoHamburgueria.getInstance().getHorarioFechamento());
    }

    @Test
    void deveRetornarExcecaoParaHorarioFechamentoVazio() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ConfiguracaoHamburgueria.getInstance().setHorarioFechamento(""));
        assertEquals("ERR02 - O horário de fechamento não pode ser nulo ou em branco!", e.getMessage());
    }

    @Test
    void deveRetornarOperadorLogado() {
        ConfiguracaoHamburgueria.getInstance().setOperadorLogado("Operador 1");
        assertEquals("Operador 1", ConfiguracaoHamburgueria.getInstance().getOperadorLogado());
    }

    @Test
    void deveRetornarExcecaoParaOperadorLogadoNulo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ConfiguracaoHamburgueria.getInstance().setOperadorLogado(null));
        assertEquals("ERR02 - O operador atual não pode ser nulo ou em branco!", e.getMessage());
    }

    @Test
    void deveRetornarAlcadaMaximaAtendente() {
        ConfiguracaoHamburgueria.getInstance().setAlcadaMaximaAtendente(50.0f);
        assertEquals(50.0f, ConfiguracaoHamburgueria.getInstance().getAlcadaMaximaAtendente());
    }

    @Test
    void deveRetornarExcecaoParaAlcadaMaximaNegativa() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ConfiguracaoHamburgueria.getInstance().setAlcadaMaximaAtendente(-10.0f));
        assertEquals("ERR03 - A alçada máxima não pode ser negativa!", e.getMessage());
    }
}
