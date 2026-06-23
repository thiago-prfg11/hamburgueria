package test.atendimento;

import main.atendimento.IServicoPedido;
import main.atendimento.ServicoPedidoFactory;
import main.pedido.EstadoPronto;
import main.pedido.EstadoSaiuParaEntrega;
import main.pedido.Pedido;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicoDeliveryTest {

    @Test
    void deveAlterarEstadoPedidoParaSaiuParaEntrega() {
        Pedido pedido = new Pedido("PED-001");
        pedido.setEstado(EstadoPronto.getInstance());
        IServicoPedido servico = ServicoPedidoFactory.obterServico("Delivery");

        servico.iniciar(pedido);

        assertEquals(EstadoSaiuParaEntrega.getInstance(), pedido.getEstado());
    }

    @Test
    void deveRetornarDescricaoDeEntregaComEmbalagemDelivery() {
        Pedido pedido = new Pedido("PED-002");
        pedido.setEstado(EstadoPronto.getInstance());
        IServicoPedido servico = ServicoPedidoFactory.obterServico("Delivery");

        String resultado = servico.iniciar(pedido);

        assertEquals("O pedido está aguardando retirada no balcão pelo entregador," +
                " embalado em Sacola Térmica com Lacre de Segurança", resultado);
    }

    @Test
    void deveCancelarDespachoDeEntrega() {
        IServicoPedido servico = ServicoPedidoFactory.obterServico("Delivery");

        assertEquals("A retirada do pedido pelo entregador foi cancelada!", servico.cancelarDespacho());
    }
}
