package test.pedido;

import main.pedido.AppClienteObserver;
import main.pedido.Pedido;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppClienteObserverTest {

    @Test
    void deveNotificarUmCliente() {
        Pedido pedido = new Pedido("PED-100");
        AppClienteObserver cliente = new AppClienteObserver("Maria");
        cliente.acompanhar(pedido);
        pedido.confirmarPreparo();
        assertEquals("Olá, Maria! Seu pedido PED-100 se encontra no seguinte estado: Em Preparo",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveNotificarVariosClientes() {
        Pedido pedido = new Pedido("PED-200");
        AppClienteObserver cliente1 = new AppClienteObserver("Maria");
        AppClienteObserver cliente2 = new AppClienteObserver("João");
        cliente1.acompanhar(pedido);
        cliente2.acompanhar(pedido);
        pedido.confirmarPreparo();
        assertEquals("Olá, Maria! Seu pedido PED-200 se encontra no seguinte estado: Em Preparo",
                cliente1.getUltimaNotificacao());
        assertEquals("Olá, João! Seu pedido PED-200 se encontra no seguinte estado: Em Preparo",
                cliente2.getUltimaNotificacao());
    }

    @Test
    void naoDeveNotificarClienteNaoAcompanhando() {
        Pedido pedido = new Pedido("PED-300");
        AppClienteObserver cliente = new AppClienteObserver("Maria");
        pedido.confirmarPreparo();
        assertNull(cliente.getUltimaNotificacao());
    }

    @Test
    void naoDeveNotificarClienteDeOutroPedido() {
        Pedido pedidoA = new Pedido("PED-400");
        Pedido pedidoB = new Pedido("PED-401");
        AppClienteObserver cliente1 = new AppClienteObserver("Maria");
        AppClienteObserver cliente2 = new AppClienteObserver("João");
        cliente1.acompanhar(pedidoA);
        cliente2.acompanhar(pedidoB);
        pedidoA.confirmarPreparo();
        assertEquals("Olá, Maria! Seu pedido PED-400 se encontra no seguinte estado: Em Preparo",
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
