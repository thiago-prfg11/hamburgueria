package test.atendimento;

import main.atendimento.IServicoPedido;
import main.atendimento.ServicoPedidoFactory;
import main.pedido.EstadoEntregue;
import main.pedido.EstadoPronto;
import main.pedido.Pedido;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicoConsumoLocalTest {

    @Test
    void deveAlterarEstadoPedidoParaEntregue() {
        Pedido pedido = new Pedido("PED-CONSUMO-001");
        pedido.setEstado(EstadoPronto.getInstance());
        IServicoPedido servico = ServicoPedidoFactory.obterServico("ConsumoLocal");
        servico.iniciar(pedido);
        assertEquals(EstadoEntregue.getInstance(), pedido.getEstado());
    }

    @Test
    void deveRetornarDescricaoDeConsumoLocalComEmbalagemBalcao() {
        Pedido pedido = new Pedido("PED-CONSUMO-002");
        pedido.setEstado(EstadoPronto.getInstance());
        IServicoPedido servico = ServicoPedidoFactory.obterServico("ConsumoLocal");
        String resultado = servico.iniciar(pedido);
        assertEquals("Pedido aguardando retirada no balcão pelo atendente," +
                " embalado em Bandeja com Guardanapo", resultado);
    }

    @Test
    void deveCancelarReservaDeMesa() {
        IServicoPedido servico = ServicoPedidoFactory.obterServico("ConsumoLocal");
        assertEquals("Retirada do pedido pelo atendente cancelada", servico.cancelarDespacho());
    }
}
