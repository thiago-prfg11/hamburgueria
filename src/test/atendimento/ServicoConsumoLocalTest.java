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
        Pedido pedido = new Pedido("PED-001");
        pedido.setEstado(EstadoPronto.getInstance());
        IServicoPedido servico = ServicoPedidoFactory.obterServico("ConsumoLocal");
        servico.iniciar(pedido);
        assertEquals(EstadoEntregue.getInstance(), pedido.getEstado());
    }

    @Test
    void deveRetornarDescricaoDeConsumoLocalComEmbalagemBalcao() {
        Pedido pedido = new Pedido("PED-002");
        pedido.setEstado(EstadoPronto.getInstance());
        IServicoPedido servico = ServicoPedidoFactory.obterServico("ConsumoLocal");
        String resultado = servico.iniciar(pedido);
        assertEquals("O pedido está aguardando retirada no balcão pelo atendente," +
                " embalado em Bandeja com Guardanapo", resultado);
    }

    @Test
    void deveCancelarReservaDeMesa() {
        IServicoPedido servico = ServicoPedidoFactory.obterServico("ConsumoLocal");
        assertEquals("A retirada do pedido pelo atendente foi cancelada!", servico.cancelarDespacho());
    }
}
