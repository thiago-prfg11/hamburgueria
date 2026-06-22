package test.pagamento;

import main.pagamento.AdaptadorCielo;
import main.pagamento.StatusPagamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdaptadorCieloTest {

    @Test
    void deveRetornarAprovadoParaValorMinimoCielo() {
        AdaptadorCielo adaptador = new AdaptadorCielo();
        assertEquals(StatusPagamento.APROVADO, adaptador.processar(1));
    }

    @Test
    void deveRetornarAprovadoParaValorDentroDoLimiteCielo() {
        AdaptadorCielo adaptador = new AdaptadorCielo();
        assertEquals(StatusPagamento.APROVADO, adaptador.processar(5000));
    }

    @Test
    void deveRetornarAprovadoParaValorMaximoCielo() {
        AdaptadorCielo adaptador = new AdaptadorCielo();
        assertEquals(StatusPagamento.APROVADO, adaptador.processar(30000));
    }

    @Test
    void deveRetornarRecusadoParaValorAcimaDoLimiteCielo() {
        AdaptadorCielo adaptador = new AdaptadorCielo();
        assertEquals(StatusPagamento.RECUSADO, adaptador.processar(30001));
    }

    @Test
    void deveRetornarErroParaValorInvalidoCielo() {
        AdaptadorCielo adaptador = new AdaptadorCielo();
        assertEquals(StatusPagamento.ERRO, adaptador.processar(0));
    }
}
