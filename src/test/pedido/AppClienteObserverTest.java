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
        Pedido pedido = new Pedido("PED-OBS-001");
        AppClienteObserver cliente = new AppClienteObserver("Maria");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);

        pedido.confirmarPreparo();

        assertEquals("Atenção, Maria, seu pedido PED-OBS-001 se encontra no seguinte estado: Em Preparo",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveNotificarClienteQuandoPedidoMudaParaPronto() {
        Pedido pedido = new Pedido("PED-OBS-002");
        AppClienteObserver cliente = new AppClienteObserver("João");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);
        pedido.confirmarPreparo();

        pedido.finalizarPreparo();

        assertEquals("Atenção, João, seu pedido PED-OBS-002 se encontra no seguinte estado: Pronto",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveNotificarClienteQuandoPedidoMudaParaSaiuParaEntrega() {
        Pedido pedido = new Pedido("PED-OBS-003");
        AppClienteObserver cliente = new AppClienteObserver("Ana");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);
        pedido.confirmarPreparo();
        pedido.finalizarPreparo();

        pedido.despachar();

        assertEquals("Atenção, Ana, seu pedido PED-OBS-003 se encontra no seguinte estado: Saiu para Entrega",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveNotificarClienteQuandoPedidoMudaParaEntregue() {
        Pedido pedido = new Pedido("PED-OBS-004");
        AppClienteObserver cliente = new AppClienteObserver("Carlos");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);
        pedido.confirmarPreparo();
        pedido.finalizarPreparo();
        pedido.despachar();

        pedido.entregar();

        assertEquals("Atenção, Carlos, seu pedido PED-OBS-004 se encontra no seguinte estado: Entregue",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveNotificarClienteQuandoPedidoMudaParaCancelado() {
        Pedido pedido = new Pedido("PED-OBS-005");
        AppClienteObserver cliente = new AppClienteObserver("Beatriz");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);

        pedido.cancelar();

        assertEquals("Atenção, Beatriz, seu pedido PED-OBS-005 se encontra no seguinte estado: Cancelado",
                cliente.getUltimaNotificacao());
    }

    @Test
    void naoDeveNotificarClienteNaoAcompanhando() {
        Pedido pedido = new Pedido("PED-OBS-006");
        AppClienteObserver cliente = new AppClienteObserver("Maria");

        pedido.confirmarPreparo();

        assertNull(cliente.getUltimaNotificacao());
    }

    @Test
    void naoDeveNotificarClienteDeOutroPedido() {
        Pedido pedidoA = new Pedido("PED-OBS-007");
        Pedido pedidoB = new Pedido("PED-OBS-008");
        AppClienteObserver cliente1 = new AppClienteObserver("Maria");
        AppClienteObserver cliente2 = new AppClienteObserver("João");
        CentralAtendimento.getInstance().acompanharPedido(pedidoA, cliente1);
        CentralAtendimento.getInstance().acompanharPedido(pedidoB, cliente2);

        pedidoA.confirmarPreparo();

        assertEquals("Atenção, Maria, seu pedido PED-OBS-007 se encontra no seguinte estado: Em Preparo",
                cliente1.getUltimaNotificacao());
        assertNull(cliente2.getUltimaNotificacao());
    }

    @Test
    void deveRetornarExcecaoParaNomeClienteNulo() {
        try {
            new AppClienteObserver(null);
        } catch (IllegalArgumentException e) {
            assertEquals("O nome do cliente não pode ser nulo ou vazio!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaNomeClienteVazio() {
        try {
            new AppClienteObserver("   ");
        } catch (IllegalArgumentException e) {
            assertEquals("O nome do cliente não pode ser nulo ou vazio!", e.getMessage());
        }
    }
}
