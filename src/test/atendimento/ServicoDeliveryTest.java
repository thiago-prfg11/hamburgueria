package test.atendimento;

import main.atendimento.IServicoPedido;
import main.atendimento.ServicoPedidoFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicoDeliveryTest {

    @Test
    void deveIniciarEntregaComEmbalagemDelivery() {
        IServicoPedido servico = ServicoPedidoFactory.obterServico("Delivery");
        assertEquals("Pedido aguardando retirada no balcão pelo entregador, embalado em Sacola Térmica com Lacre de Segurança", servico.iniciar());
    }

    @Test
    void deveCancelarDespachoDeEntrega() {
        IServicoPedido servico = ServicoPedidoFactory.obterServico("Delivery");
        assertEquals("Retirada do pedido pelo entregador cancelada", servico.cancelarDespacho());
    }
}
