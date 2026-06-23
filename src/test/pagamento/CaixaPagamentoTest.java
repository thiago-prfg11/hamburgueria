package test.pagamento;

import main.pagamento.CaixaPagamento;
import main.pagamento.StatusPagamento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaixaPagamentoTest {

    @Test
    void deveConverterValorEmReaisParaCentavosAoProcessar() {
        int[] valorRecebido = new int[1];
        CaixaPagamento caixa = new CaixaPagamento(valorTransacao -> {
            valorRecebido[0] = valorTransacao;
            return StatusPagamento.APROVADO;
        });

        caixa.processarPagamento(22.90f);

        assertEquals(2290, valorRecebido[0]);
    }

    @Test
    void deveRetornarStatusRetornadoPeloProcessador() {
        CaixaPagamento caixa = new CaixaPagamento(valorTransacao -> StatusPagamento.RECUSADO);

        StatusPagamento status = caixa.processarPagamento(50.0f);

        assertEquals(StatusPagamento.RECUSADO, status);
    }

    @Test
    void deveRetornarExcecaoParaProcessadoraNula() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new CaixaPagamento(null));
        assertEquals("ERR01 - O processador de pagamento referenciado não pode ser nulo!", e.getMessage());
    }
}