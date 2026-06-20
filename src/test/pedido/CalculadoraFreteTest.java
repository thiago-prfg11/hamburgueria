package test.pedido;

import main.configuracao.ConfiguracaoHamburgueria;
import main.pedido.CalculadoraFrete;
import main.pedido.FreteFixo;
import main.pedido.FreteGratisAcimaDeValor;
import main.pedido.FretePorKm;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculadoraFreteTest {

    @Test
    void deveCalcularFreteFixoComBaseNaConfiguracao() {
        ConfiguracaoHamburgueria.getInstance().setTaxaEntregaPadrao(7.90f);
        CalculadoraFrete calculadora = new CalculadoraFrete(12.0f, 50.0f);
        assertEquals(7.90f, calculadora.calcularFrete(new FreteFixo()));
    }

    @Test
    void deveCalcularFretePorKm() {
        CalculadoraFrete calculadora = new CalculadoraFrete(8.0f, 50.0f);
        assertEquals(20.0f, calculadora.calcularFrete(new FretePorKm(2.50f)));
    }

    @Test
    void deveAplicarFreteGratisQuandoValorPedidoAtingeMinimo() {
        CalculadoraFrete calculadora = new CalculadoraFrete(5.0f, 100.0f);
        assertEquals(0f, calculadora.calcularFrete(new FreteGratisAcimaDeValor(80.0f, 12.0f)));
    }

    @Test
    void deveAplicarFretePadraoQuandoValorPedidoNaoAtingeMinimo() {
        CalculadoraFrete calculadora = new CalculadoraFrete(5.0f, 50.0f);
        assertEquals(12.0f, calculadora.calcularFrete(new FreteGratisAcimaDeValor(80.0f, 12.0f)));
    }

    @Test
    void deveAplicarFreteGratisQuandoValorPedidoEhExatamenteOMinimo() {
        CalculadoraFrete calculadora = new CalculadoraFrete(5.0f, 80.0f);
        assertEquals(0f, calculadora.calcularFrete(new FreteGratisAcimaDeValor(80.0f, 12.0f)));
    }

    @Test
    void deveRetornarExcecaoParaDistanciaNegativaNoConstrutor() {
        try {
            new CalculadoraFrete(-1.0f, 50.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("A distância para entrega não pode ser negativa!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaValorPedidoNegativoNoConstrutor() {
        try {
            new CalculadoraFrete(5.0f, -10.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O valor do pedido não pode ser negativo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaEstrategiaNula() {
        CalculadoraFrete calculadora = new CalculadoraFrete(5.0f, 50.0f);
        try {
            calculadora.calcularFrete(null);
        } catch (IllegalArgumentException e) {
            assertEquals("A estratégia de entrega informada não pode ser nula!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaValorPorKmNegativo() {
        try {
            new FretePorKm(-2.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O valor do quilômetro rodado não pode ser negativo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaValorMinimoFreteGratisNegativo() {
        try {
            new FreteGratisAcimaDeValor(-50.0f, 10.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O valor mínimo para o frete grátis não pode ser negativo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaValorFretePadraoNegativo() {
        try {
            new FreteGratisAcimaDeValor(80.0f, -10.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O valor padrão do frete não pode ser negativo!", e.getMessage());
        }
    }
}
