package test.atendimento;

import main.atendimento.IServicoPedido;
import main.atendimento.ServicoPedidoFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicoRetiradaTest {

    @Test
    void deveIniciarRetiradaComEmbalagemBalcao() {
        IServicoPedido servico = ServicoPedidoFactory.obterServico("Retirada");
        assertEquals("Pedido aguardando retirada no balcão pelo cliente, embalado em Bandeja com Guardanapo", servico.iniciar());
    }

    @Test
    void deveCancelarRetiradaPeloCliente() {
        IServicoPedido servico = ServicoPedidoFactory.obterServico("Retirada");
        assertEquals("Retirada do pedido pelo cliente cancelada", servico.cancelarDespacho());
    }
}
