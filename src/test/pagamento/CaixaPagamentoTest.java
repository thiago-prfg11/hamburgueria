package test.pagamento;

import main.pagamento.CaixaPagamento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaixaPagamentoTest {

    @Test
    void deveRetornarExcecaoParaProcessadoraNula() {
        try {
            new CaixaPagamento(null);

        } catch (IllegalArgumentException e) {
            assertEquals("O processador de pagamento referenciado é inválido!", e.getMessage());
        }
    }
}