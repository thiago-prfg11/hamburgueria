package test.pagamento;

import main.pagamento.AdaptadorGertec;
import main.pagamento.StatusPagamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdaptadorGertecTest {

    @Test
    void deveRetornarAprovadoParaValorMinimoGertec() {
        AdaptadorGertec adaptador = new AdaptadorGertec();
        assertEquals(StatusPagamento.APROVADO, adaptador.processar(1));
    }

    @Test
    void deveRetornarAprovadoParaValorDentroDoLimiteGertec() {
        AdaptadorGertec adaptador = new AdaptadorGertec();
        assertEquals(StatusPagamento.APROVADO, adaptador.processar(5000));
    }

    @Test
    void deveRetornarAprovadoParaValorMaximoGertec() {
        AdaptadorGertec adaptador = new AdaptadorGertec();
        assertEquals(StatusPagamento.APROVADO, adaptador.processar(15000));
    }

    @Test
    void deveRetornarRecusadoParaValorAcimaDoLimiteGertec() {
        AdaptadorGertec adaptador = new AdaptadorGertec();
        assertEquals(StatusPagamento.RECUSADO, adaptador.processar(15001));
    }

    @Test
    void deveRetornarErroParaValorInvalidoGertec() {
        AdaptadorGertec adaptador = new AdaptadorGertec();
        assertEquals(StatusPagamento.ERRO, adaptador.processar(0));
    }
}
