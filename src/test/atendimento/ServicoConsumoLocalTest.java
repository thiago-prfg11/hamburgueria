package test.atendimento;

import main.atendimento.IServicoPedido;
import main.atendimento.ServicoPedidoFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicoConsumoLocalTest {

    @Test
    void deveIniciarConsumoLocalComEmbalagemBalcao() {
        IServicoPedido servico = ServicoPedidoFactory.obterServico("ConsumoLocal");

        assertEquals("Pedido aguardando retirada no balcão pelo atendente, embalado em Bandeja com Guardanapo", servico.iniciar());
    }

    @Test
    void deveCancelarRetiradaPeloAtendente() {
        IServicoPedido servico = ServicoPedidoFactory.obterServico("ConsumoLocal");

        assertEquals("Retirada do pedido pelo atendente cancelada", servico.cancelarDespacho());
    }
}
