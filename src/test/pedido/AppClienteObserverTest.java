package test.pedido;

import main.atendimento.CentralAtendimento;
import main.pedido.AppClienteObserver;
import main.pedido.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppClienteObserverTest {

    @BeforeEach
    void setUp() {
        CentralAtendimento.getInstance().limpar();
    }

    @Test
    void deveNotificarClienteQuandoPedidoMudaParaEmPreparo() {
        Pedido pedido = new Pedido("PED-001");
        AppClienteObserver cliente = new AppClienteObserver("Maria");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);

        pedido.confirmarPreparo();

        assertEquals("Atenção, Maria, seu pedido PED-001 se encontra no seguinte estado: Em Preparo",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveNotificarClienteQuandoPedidoMudaParaPronto() {
        Pedido pedido = new Pedido("PED-002");
        AppClienteObserver cliente = new AppClienteObserver("João");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);
        pedido.confirmarPreparo();

        pedido.finalizarPreparo();

        assertEquals("Atenção, João, seu pedido PED-002 se encontra no seguinte estado: Pronto",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveNotificarClienteQuandoPedidoMudaParaSaiuParaEntrega() {
        Pedido pedido = new Pedido("PED-003");
        AppClienteObserver cliente = new AppClienteObserver("Ana");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);
        pedido.confirmarPreparo();
        pedido.finalizarPreparo();

        pedido.despachar();

        assertEquals("Atenção, Ana, seu pedido PED-003 se encontra no seguinte estado: Saiu para Entrega",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveNotificarClienteQuandoPedidoMudaParaEntregue() {
        Pedido pedido = new Pedido("PED-004");
        AppClienteObserver cliente = new AppClienteObserver("Carlos");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);
        pedido.confirmarPreparo();
        pedido.finalizarPreparo();
        pedido.despachar();

        pedido.entregar();

        assertEquals("Atenção, Carlos, seu pedido PED-004 se encontra no seguinte estado: Entregue",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveNotificarClienteQuandoPedidoMudaParaCancelado() {
        Pedido pedido = new Pedido("PED-005");
        AppClienteObserver cliente = new AppClienteObserver("Beatriz");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);

        pedido.cancelar();

        assertEquals("Atenção, Beatriz, seu pedido PED-005 se encontra no seguinte estado: Cancelado",
                cliente.getUltimaNotificacao());
    }

    @Test
    void naoDeveNotificarClienteNaoAcompanhando() {
        Pedido pedido = new Pedido("PED-006");
        AppClienteObserver cliente = new AppClienteObserver("Maria");

        pedido.confirmarPreparo();

        assertNull(cliente.getUltimaNotificacao());
    }

    @Test
    void naoDeveNotificarClienteDeOutroPedido() {
        Pedido pedidoA = new Pedido("PED-007");
        Pedido pedidoB = new Pedido("PED-008");
        AppClienteObserver cliente1 = new AppClienteObserver("Irene");
        AppClienteObserver cliente2 = new AppClienteObserver("Maria Isabel");
        CentralAtendimento.getInstance().acompanharPedido(pedidoA, cliente1);
        CentralAtendimento.getInstance().acompanharPedido(pedidoB, cliente2);

        pedidoA.confirmarPreparo();

        assertEquals("Atenção, Irene, seu pedido PED-007 se encontra no seguinte estado: Em Preparo",
                cliente1.getUltimaNotificacao());
        assertNull(cliente2.getUltimaNotificacao());
    }

    @Test
    void deveRetornarExcecaoParaNomeClienteNulo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new AppClienteObserver(null));
        assertEquals("ERR02 - O nome do cliente não pode ser nulo ou vazio!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaNomeClienteVazio() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new AppClienteObserver("   "));
        assertEquals("ERR02 - O nome do cliente não pode ser nulo ou vazio!", e.getMessage());
    }
}
