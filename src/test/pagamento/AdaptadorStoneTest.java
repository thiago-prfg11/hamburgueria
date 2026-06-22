package test.pagamento;

import main.pagamento.AdaptadorStone;
import main.pagamento.StatusPagamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdaptadorStoneTest {

    @Test
    void deveRetornarAprovadoParaValorMinimoStone() {
        AdaptadorStone adaptador = new AdaptadorStone();
        assertEquals(StatusPagamento.APROVADO, adaptador.processar(1));
    }

    @Test
    void deveRetornarAprovadoParaValorDentroDoLimiteStone() {
        AdaptadorStone adaptador = new AdaptadorStone();
        assertEquals(StatusPagamento.APROVADO, adaptador.processar(5000));
    }

    @Test
    void deveRetornarAprovadoParaValorMaximoStone() {
        AdaptadorStone adaptador = new AdaptadorStone();
        assertEquals(StatusPagamento.APROVADO, adaptador.processar(50000));
    }

    @Test
    void deveRetornarRecusadoParaValorAcimaDoLimiteStone() {
        AdaptadorStone adaptador = new AdaptadorStone();
        assertEquals(StatusPagamento.RECUSADO, adaptador.processar(50001));
    }

    @Test
    void deveRetornarErroParaValorInvalidoStone() {
        AdaptadorStone adaptador = new AdaptadorStone();
        assertEquals(StatusPagamento.ERRO, adaptador.processar(0));
    }
}