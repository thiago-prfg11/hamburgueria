package test.atendimento;

import main.atendimento.ServicoPedidoFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicoPedidoFactoryTest {

    @Test
    void deveRetornarExcecaoParaServicoInexistente() {
        try {
            ServicoPedidoFactory.obterServico("Teleporte");
        } catch (IllegalArgumentException e) {
            assertEquals("O serviço escolhido não existe!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaServicoInvalido() {
        try {
            ServicoPedidoFactory.obterServico("DriveThru");
        } catch (IllegalArgumentException e) {
            assertEquals("O serviço escolhido é inválido!", e.getMessage());
        }
    }
}
