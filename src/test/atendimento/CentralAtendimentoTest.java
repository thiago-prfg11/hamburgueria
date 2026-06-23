package test.atendimento;

import main.atendimento.CentralAtendimento;
import main.atendimento.CozinhaObserver;
import main.cardapio.*;
import main.pedido.AppClienteObserver;
import main.pedido.FreteFixo;
import main.pedido.Pedido;
import main.pedido.PedidoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CentralAtendimentoTest {

    CatalogoReceitas catalogo;

    @BeforeEach
    void setUp() {
        CentralAtendimento.getInstance().limpar();
        catalogo = new CatalogoReceitas();
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Burguer", ingredientes, 22.90f,
                12, TecnicaPreparo.TRADICIONAL));
    }

    @Test
    void deveNotificarCozinhaApenasQuandoEmPreparo() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-001")
                .setNomeCliente("Bruno")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burguer")))
                .build();
        CozinhaObserver cozinha = new CozinhaObserver(catalogo);
        CentralAtendimento.getInstance().acompanharPedido(pedido, cozinha);

        pedido.confirmarPreparo();

        assertEquals(1, cozinha.getPainelCozinha().getHistorico().size());
    }

    @Test
    void naoDeveNotificarCozinhaQuandoPronto() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-002")
                .setNomeCliente("Carla")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burguer")))
                .build();
        CozinhaObserver cozinha = new CozinhaObserver(catalogo);
        CentralAtendimento.getInstance().acompanharPedido(pedido, cozinha);
        pedido.confirmarPreparo();

        pedido.finalizarPreparo();

        assertEquals(0, cozinha.getPainelCozinha().getFilaEspera().size());
        assertEquals(1, cozinha.getPainelCozinha().getHistorico().size());
    }

    @Test
    void deveNotificarClienteECozinhaSimultaneamenteEmPreparo() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-003")
                .setNomeCliente("Daniel")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burguer")))
                .build();
        AppClienteObserver cliente = new AppClienteObserver("Daniel");
        CozinhaObserver cozinha = new CozinhaObserver(catalogo);
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);
        CentralAtendimento.getInstance().acompanharPedido(pedido, cozinha);

        pedido.confirmarPreparo();

        assertEquals("Atenção, Daniel, seu pedido PED-003 se encontra no seguinte estado: Em Preparo", cliente.getUltimaNotificacao());
        assertEquals(1, cozinha.getPainelCozinha().getHistorico().size());
    }

    @Test
    void deveCozinhaNaoAtuarQuandoCancelamentoForDeRecebido() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-004")
                .setNomeCliente("Elisa")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burguer")))
                .build();
        CozinhaObserver cozinha = new CozinhaObserver(catalogo);
        CentralAtendimento.getInstance().acompanharPedido(pedido, cozinha);

        pedido.cancelar();

        assertEquals(0, cozinha.getPainelCozinha().getFilaEspera().size());
        assertEquals(0, cozinha.getPainelCozinha().getHistorico().size());
    }

    @Test
    void deveCozinhaDesfazerTarefaQuandoCanceladoEmPreparo() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-005")
                .setNomeCliente("Felipe")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burguer")))
                .build();
        CozinhaObserver cozinha = new CozinhaObserver(catalogo);
        CentralAtendimento.getInstance().acompanharPedido(pedido, cozinha);
        pedido.confirmarPreparo();

        pedido.cancelar();

        assertEquals(1, cozinha.getPainelCozinha().getFilaEspera().size());
        assertEquals(0, cozinha.getPainelCozinha().getHistorico().size());
    }

    @Test
    void deveRegistrarEventoNoHistoricoAoMudarEstado() {
        Pedido pedido = new Pedido("PED-006");
        AppClienteObserver cliente = new AppClienteObserver("Gabriela");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);

        pedido.confirmarPreparo();

        assertEquals(1, CentralAtendimento.getInstance().getHistoricoPedido("PED-006").size());
    }

    @Test
    void deveRegistrarDescricaoCorretaNoHistorico() {
        Pedido pedido = new Pedido("PED-007");
        AppClienteObserver cliente = new AppClienteObserver("Henrique");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);

        pedido.confirmarPreparo();

        assertEquals("Pedido PED-007 está: Em Preparo",
                CentralAtendimento.getInstance().getHistoricoPedido("PED-007").getFirst());
    }

    @Test
    void deveRegistrarMultiplosEventosNoHistorico() {
        Pedido pedido = new Pedido("PED-008");
        AppClienteObserver cliente = new AppClienteObserver("Helena");
        CentralAtendimento.getInstance().acompanharPedido(pedido, cliente);

        pedido.confirmarPreparo();
        pedido.finalizarPreparo();

        assertEquals(2, CentralAtendimento.getInstance().getHistoricoPedido("PED-008").size());
    }

    @Test
    void deveManterHistoricosSeparadosPorPedido() {
        Pedido pedido1 = new Pedido("PED-009");
        Pedido pedido2 = new Pedido("PED-010");
        AppClienteObserver cliente1 = new AppClienteObserver("Igor");
        AppClienteObserver cliente2 = new AppClienteObserver("Julia");
        CentralAtendimento.getInstance().acompanharPedido(pedido1, cliente1);
        CentralAtendimento.getInstance().acompanharPedido(pedido2, cliente2);

        pedido1.confirmarPreparo();
        pedido2.confirmarPreparo();
        pedido2.finalizarPreparo();

        assertEquals(1, CentralAtendimento.getInstance().getHistoricoPedido("PED-009").size());
        assertEquals(2, CentralAtendimento.getInstance().getHistoricoPedido("PED-010").size());
    }

    @Test
    void deveRetornarExcecaoParaPedidoNuloEmAcompanharPedido() {
        AppClienteObserver cliente = new AppClienteObserver("Lucas");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CentralAtendimento.getInstance().acompanharPedido(null, cliente));
        assertEquals("ERR01 - O pedido referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaObserverNuloEmAcompanharPedido() {
        Pedido pedido = new Pedido("PED-012");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CentralAtendimento.getInstance().acompanharPedido(pedido, null));
        assertEquals("ERR01 - O observador referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCodigoNuloEmGetHistorico() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> CentralAtendimento.getInstance().getHistoricoPedido(null));
        assertEquals("ERR02 - O código do pedido referenciado não pode ser nulo ou vazio!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCatalogoNuloNoCozinhaObserver() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new CozinhaObserver(null));
        assertEquals("ERR01 - O catálogo de receitas referenciado não pode ser nulo!", e.getMessage());
    }
}