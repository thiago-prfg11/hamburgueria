package test.atendimento;

import main.atendimento.IServicoPedido;
import main.atendimento.ServicoPedidoFactory;
import main.pedido.EstadoEntregue;
import main.pedido.EstadoPronto;
import main.pedido.Pedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicoRetiradaTest {

    @Test
    void deveAlterarEstadoPedidoParaEntregue() {
        Pedido pedido = new Pedido("PED-001");
        pedido.setEstado(EstadoPronto.getInstance());
        IServicoPedido servico = ServicoPedidoFactory.obterServico("Retirada");
        servico.iniciar(pedido);
        assertEquals(EstadoEntregue.getInstance(), pedido.getEstado());
    }

    @Test
    void deveRetornarDescricaoDeRetiradaComEmbalagemBalcao() {
        Pedido pedido = new Pedido("PED-002");
        pedido.setEstado(EstadoPronto.getInstance());
        IServicoPedido servico = ServicoPedidoFactory.obterServico("Retirada");

        String resultado = servico.iniciar(pedido);

        assertEquals("O pedido está aguardando retirada no balcão pelo cliente, embalado em Bandeja com Guardanapo", resultado);
    }

    @Test
    void deveCancelarReservaDeRetirada() {
        IServicoPedido servico = ServicoPedidoFactory.obterServico("Retirada");

        assertEquals("A retirada do pedido pelo cliente foi cancelada!", servico.cancelarDespacho());
    }
}
