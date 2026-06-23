package test.pedido;

import main.pedido.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido("PED-001");
    }

    @Test
    void deveIniciarComEstadoRecebidoComoInstancia() {
        assertSame(EstadoRecebido.getInstance(), pedido.getEstado());
    }

    @Test
    void deveIniciarComDescricaoEstadoRecebido() {
        assertEquals("Recebido", pedido.getDescricaoEstado());
    }

    @Test
    void deveRetornarExcecaoParaCodigoPedidoNulo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Pedido(null));
        assertEquals("ERR02 - O código do pedido não pode ser nulo ou vazio!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCodigoPedidoVazio() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Pedido("   "));
        assertEquals("ERR02 - O código do pedido não pode ser nulo ou vazio!", e.getMessage());
    }

    // Testes Para Pedido [RECEBIDO]

    @Test
    void deveConfirmarPreparoPedidoRecebido() {
        pedido.setEstado(EstadoRecebido.getInstance());
        assertTrue(pedido.confirmarPreparo());
    }

    @Test
    void deveAlterarEstadoParaEmPreparoAoConfirmarPreparo() {
        pedido.setEstado(EstadoRecebido.getInstance());
        pedido.confirmarPreparo();
        assertEquals(EstadoEmPreparo.getInstance(), pedido.getEstado());
    }

    @Test
    void naoDeveFinalizarPreparoPedidoRecebido() {
        pedido.setEstado(EstadoRecebido.getInstance());
        assertFalse(pedido.finalizarPreparo());
    }

    @Test
    void naoDeveDespacharPedidoRecebido() {
        pedido.setEstado(EstadoRecebido.getInstance());
        assertFalse(pedido.despachar());
    }

    @Test
    void naoDeveEntregarPedidoRecebido() {
        pedido.setEstado(EstadoRecebido.getInstance());
        assertFalse(pedido.entregar());
    }

    @Test
    void deveCancelarPedidoRecebido() {
        pedido.setEstado(EstadoRecebido.getInstance());
        assertTrue(pedido.cancelar());
    }

    @Test
    void deveAlterarEstadoParaCanceladoAoCancelarRecebido() {
        pedido.setEstado(EstadoRecebido.getInstance());
        pedido.cancelar();
        assertEquals(EstadoCancelado.getInstance(), pedido.getEstado());
    }

    // Testes Para Pedido [EM PREPARO]

    @Test
    void naoDeveConfirmarPreparoPedidoEmPreparo() {
        pedido.setEstado(EstadoEmPreparo.getInstance());
        assertFalse(pedido.confirmarPreparo());
    }

    @Test
    void deveFinalizarPreparoPedidoEmPreparo() {
        pedido.setEstado(EstadoEmPreparo.getInstance());
        assertTrue(pedido.finalizarPreparo());
    }

    @Test
    void deveAlterarEstadoParaProntoAoFinalizarPreparo() {
        pedido.setEstado(EstadoEmPreparo.getInstance());
        pedido.finalizarPreparo();
        assertEquals(EstadoPronto.getInstance(), pedido.getEstado());
    }

    @Test
    void naoDeveDespacharPedidoEmPreparo() {
        pedido.setEstado(EstadoEmPreparo.getInstance());
        assertFalse(pedido.despachar());
    }

    @Test
    void naoDeveEntregarPedidoEmPreparo() {
        pedido.setEstado(EstadoEmPreparo.getInstance());
        assertFalse(pedido.entregar());
    }

    @Test
    void deveCancelarPedidoEmPreparo() {
        pedido.setEstado(EstadoEmPreparo.getInstance());
        assertTrue(pedido.cancelar());
    }

    @Test
    void deveAlterarEstadoParaCanceladoAoCancelarEmPreparo() {
        pedido.setEstado(EstadoEmPreparo.getInstance());
        pedido.cancelar();
        assertEquals(EstadoCancelado.getInstance(), pedido.getEstado());
    }

    // Testes Para Pedido [PRONTO]

    @Test
    void naoDeveConfirmarPreparoPedidoPronto() {
        pedido.setEstado(EstadoPronto.getInstance());
        assertFalse(pedido.confirmarPreparo());
    }

    @Test
    void naoDeveFinalizarPreparoPedidoPronto() {
        pedido.setEstado(EstadoPronto.getInstance());
        assertFalse(pedido.finalizarPreparo());
    }

    @Test
    void deveDespacharPedidoPronto() {
        pedido.setEstado(EstadoPronto.getInstance());
        assertTrue(pedido.despachar());
    }

    @Test
    void deveAlterarEstadoParaSaiuParaEntregaAoDespacharPronto() {
        pedido.setEstado(EstadoPronto.getInstance());
        pedido.despachar();
        assertEquals(EstadoSaiuParaEntrega.getInstance(), pedido.getEstado());
    }

    @Test
    void deveEntregarPedidoPronto() {
        pedido.setEstado(EstadoPronto.getInstance());
        assertTrue(pedido.entregar());
    }

    @Test
    void deveAlterarEstadoParaEntregueAoEntregarPronto() {
        pedido.setEstado(EstadoPronto.getInstance());
        pedido.entregar();
        assertEquals(EstadoEntregue.getInstance(), pedido.getEstado());
    }

    @Test
    void deveCancelarPedidoPronto() {
        pedido.setEstado(EstadoPronto.getInstance());
        assertTrue(pedido.cancelar());
    }

    // Testes Para Pedido [SAIU PARA ENTREGA]

    @Test
    void naoDeveConfirmarPreparoPedidoSaiuParaEntrega() {
        pedido.setEstado(EstadoSaiuParaEntrega.getInstance());
        assertFalse(pedido.confirmarPreparo());
    }

    @Test
    void naoDeveFinalizarPreparoPedidoSaiuParaEntrega() {
        pedido.setEstado(EstadoSaiuParaEntrega.getInstance());
        assertFalse(pedido.finalizarPreparo());
    }

    @Test
    void naoDeveDespacharPedidoSaiuParaEntrega() {
        pedido.setEstado(EstadoSaiuParaEntrega.getInstance());
        assertFalse(pedido.despachar());
    }

    @Test
    void deveEntregarPedidoSaiuParaEntrega() {
        pedido.setEstado(EstadoSaiuParaEntrega.getInstance());
        assertTrue(pedido.entregar());
    }

    @Test
    void deveAlterarEstadoParaEntregueAoEntregarSaiuParaEntrega() {
        pedido.setEstado(EstadoSaiuParaEntrega.getInstance());
        pedido.entregar();
        assertEquals(EstadoEntregue.getInstance(), pedido.getEstado());
    }

    @Test
    void naoDeveCancelarPedidoSaiuParaEntrega() {
        pedido.setEstado(EstadoSaiuParaEntrega.getInstance());
        assertFalse(pedido.cancelar());
    }

    // Testes Para Pedido [ENTREGUE]

    @Test
    void naoDeveConfirmarPreparoPedidoEntregue() {
        pedido.setEstado(EstadoEntregue.getInstance());
        assertFalse(pedido.confirmarPreparo());
    }

    @Test
    void naoDeveFinalizarPreparoPedidoEntregue() {
        pedido.setEstado(EstadoEntregue.getInstance());
        assertFalse(pedido.finalizarPreparo());
    }

    @Test
    void naoDeveDespacharPedidoEntregue() {
        pedido.setEstado(EstadoEntregue.getInstance());
        assertFalse(pedido.despachar());
    }

    @Test
    void naoDeveEntregarPedidoEntregue() {
        pedido.setEstado(EstadoEntregue.getInstance());
        assertFalse(pedido.entregar());
    }

    @Test
    void naoDeveCancelarPedidoEntregue() {
        pedido.setEstado(EstadoEntregue.getInstance());
        assertFalse(pedido.cancelar());
    }

    // Testes Para Pedido [CANCELADO]

    @Test
    void naoDeveConfirmarPreparoPedidoCancelado() {
        pedido.setEstado(EstadoCancelado.getInstance());
        assertFalse(pedido.confirmarPreparo());
    }

    @Test
    void naoDeveFinalizarPreparoPedidoCancelado() {
        pedido.setEstado(EstadoCancelado.getInstance());
        assertFalse(pedido.finalizarPreparo());
    }

    @Test
    void naoDeveDespacharPedidoCancelado() {
        pedido.setEstado(EstadoCancelado.getInstance());
        assertFalse(pedido.despachar());
    }

    @Test
    void naoDeveEntregarPedidoCancelado() {
        pedido.setEstado(EstadoCancelado.getInstance());
        assertFalse(pedido.entregar());
    }

    @Test
    void naoDeveCancelarPedidoCancelado() {
        pedido.setEstado(EstadoCancelado.getInstance());
        assertFalse(pedido.cancelar());
    }
}
