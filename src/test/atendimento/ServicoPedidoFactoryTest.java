package test.atendimento;

import main.atendimento.ServicoPedidoFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicoPedidoFactoryTest {

    @Test
    void deveRetornarExcecaoParaServicoInexistente() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ServicoPedidoFactory.obterServico("Teleporte"));
        assertEquals("ERR06 - O serviço referenciado não existe no sistema!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaServicoInvalido() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ServicoPedidoFactory.obterServico("DriveThru"));
        assertEquals("ERR07 - O serviço referenciado é inválido!", e.getMessage());
    }
}
