package test.operacional;

import main.atendimento.CentralAtendimento;
import main.cardapio.*;
import main.funcionario.Atendente;
import main.funcionario.Gerente;
import main.funcionario.RegimeCLT;
import main.operacional.HamburgueriaFacade;
import main.pagamento.AdaptadorGertec;
import main.pagamento.AdaptadorStone;
import main.pagamento.IProcessadorPagamento;
import main.pedido.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class HamburgueriaFacadeTest {

    CatalogoReceitas catalogo;
    AppClienteObserver cliente;
    IProcessadorPagamento processadorAprovado;
    IProcessadorPagamento processadorRecusado;
    Pedido pedidoComLanche;
    Pedido pedidoSomenteComBebida;

    @BeforeEach
    void setUp() {
        CentralAtendimento.getInstance().limpar();

        catalogo = new CatalogoReceitas();
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false));
        catalogo.cadastrarReceita(new ReceitaLanche("Tradicional da Casa", ingredientes, 22.90f,
                12, TecnicaPreparo.TRADICIONAL));

        cliente = new AppClienteObserver("Lineu Silva");
        processadorAprovado = new AdaptadorStone();
        processadorRecusado = new AdaptadorGertec();

        pedidoComLanche = new PedidoBuilder()
                .setCodigoPedido("PED-001")
                .setNomeCliente("Lineu Silva")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("Tradicional da Casa")))
                .build();

        pedidoSomenteComBebida = new PedidoBuilder()
                .setCodigoPedido("PED002")
                .setNomeCliente("Lineu Silva")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Guaraná Antarctica – Lata 350 ml", 6.0f, 140))
                .build();
    }

    @Test
    void deveRetornarCupomComHeaderCorreto() {
        List<String> cupom = HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        assertEquals("--- Cupom Fiscal ---", cupom.getFirst());
    }

    @Test
    void deveRetornarCupomComCodigoDoPedido() {
        List<String> cupom = HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        assertEquals("Pedido: PED-001", cupom.get(1));
    }

    @Test
    void deveRetornarCupomComItemNoFormatoDoVisitor() {
        List<String> cupom = HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        assertEquals("Tradicional da Casa ... R$ 22.90", cupom.get(2));
    }

    @Test
    void deveRetornarCupomComTotalDoPedido() {
        List<String> cupom = HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        float totalEsperado = pedidoComLanche.getValorTotal();
        assertEquals("Total: R$ " + String.format(Locale.US, "%.2f", totalEsperado),
                cupom.get(cupom.size() - 2));
    }

    @Test
    void deveRetornarCupomComFooterCorreto() {
        List<String> cupom = HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        assertEquals("--------------------", cupom.getLast());
    }

    @Test
    void deveRegistrarEmPreparoNoHistorico() {
        HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        List<String> historico = CentralAtendimento.getInstance().getHistoricoPedido("PED-001");
        assertEquals("Pedido PED-001 está: Em Preparo", historico.getFirst());
    }

    @Test
    void deveRegistrarProntoNoHistorico() {
        HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        List<String> historico = CentralAtendimento.getInstance().getHistoricoPedido("PED-001");
        assertEquals("Pedido PED-001 está: Pronto", historico.get(1));
    }

    @Test
    void deveRegistrarSaiuParaEntregaNoHistorico() {
        HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        List<String> historico = CentralAtendimento.getInstance().getHistoricoPedido("PED-001");
        assertEquals("Pedido PED-001 está: Saiu para Entrega", historico.get(2));
    }

    @Test
    void deveAlterarEstadoParaSaiuParaEntregaNoDelivery() {
        HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        assertEquals(EstadoSaiuParaEntrega.getInstance(), pedidoComLanche.getEstado());
    }

    @Test
    void deveAlterarEstadoParaEntregueNaRetirada() {
        HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Retirada", processadorAprovado, catalogo, cliente);

        assertEquals(EstadoEntregue.getInstance(), pedidoComLanche.getEstado());
    }

    @Test
    void deveAlterarEstadoParaEntregueNoConsumoLocal() {
        HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "ConsumoLocal", processadorAprovado, catalogo, cliente);

        assertEquals(EstadoEntregue.getInstance(), pedidoComLanche.getEstado());
    }

    @Test
    void deveNotificarClienteAoFinalizarPedido() {
        HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        assertEquals("Atenção, Lineu Silva, seu pedido PED-001 se encontra no seguinte estado:" +
                        " Saiu para Entrega",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveRetornarCupomNullQuandoPagamentoForRecusado() {
        List<Ingrediente> ingredientesCaros = new ArrayList<>();
        ingredientesCaros.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 150, false));
        ingredientesCaros.add(IngredienteFactory.getIngrediente("Sadia – Filé de Peito Empanado Crocante", 400, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Crispy", ingredientesCaros, 200.0f,
                20, TecnicaPreparo.CHICKEN));

        Pedido pedidoCaro = new PedidoBuilder()
                .setCodigoPedido("PED-003")
                .setNomeCliente("Lineu Silva")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Crispy")))
                .build();

        List<String> cupom = HamburgueriaFacade.finalizarPedido(
                pedidoCaro, "Delivery", processadorRecusado, catalogo, cliente);

        assertNull(cupom);
    }

    @Test
    void deveCancelarPedidoQuandoPagamentoForRecusado() {
        List<Ingrediente> ingredientesCaros = new ArrayList<>();
        ingredientesCaros.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 150, false));
        ingredientesCaros.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 400, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Angus", ingredientesCaros, 200.0f,
                20, TecnicaPreparo.TRADICIONAL));

        Pedido pedidoCaro = new PedidoBuilder()
                .setCodigoPedido("PED-004")
                .setNomeCliente("Lineu Silva")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Angus")))
                .build();

        HamburgueriaFacade.finalizarPedido(
                pedidoCaro, "Delivery", processadorRecusado, catalogo, cliente);

        assertEquals(EstadoCancelado.getInstance(), pedidoCaro.getEstado());
    }

    @Test
    void deveSolicitarDescontoAprovadoPeloAtendente() {
        String resultado = HamburgueriaFacade.solicitarDesconto(pedidoComLanche, 5.0f);

        assertEquals("Atendente", resultado);
    }

    @Test
    void deveSolicitarDescontoAprovadoPeloSupervisor() {
        String resultado = HamburgueriaFacade.solicitarDesconto(pedidoComLanche, 10.0f);

        assertEquals("Supervisor", resultado);
    }

    @Test
    void deveSolicitarDescontoAprovadoPeloGerente() {
        String resultado = HamburgueriaFacade.solicitarDesconto(pedidoComLanche, 25.0f);

        assertEquals("Gerente", resultado);
    }

    @Test
    void deveSolicitarDescontoNaoAutorizado() {
        String resultado = HamburgueriaFacade.solicitarDesconto(pedidoComLanche, 31.0f);

        assertEquals("Pedido de Desconto Negado!", resultado);
    }

    @Test
    void deveConsultarRelatorioParaGerente() {
        Gerente gerente = new Gerente(6000.0f);
        gerente.setRegimeContratacao(new RegimeCLT());
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedidoComLanche);

        List<String> relatorio = HamburgueriaFacade.consultarRelatorio(pedidos, gerente);

        assertEquals(3, relatorio.size());
    }

    @Test
    void deveConsultarRelatorioParaAtendente() {
        Atendente atendente = new Atendente(1500.0f);
        atendente.setRegimeContratacao(new RegimeCLT());
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedidoComLanche);

        List<String> relatorio = HamburgueriaFacade.consultarRelatorio(pedidos, atendente);

        assertEquals(1, relatorio.size());
    }

    @Test
    void deveRetornarExcecaoParaPedidoNuloEmFinalizarPedido() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> HamburgueriaFacade.finalizarPedido(null, "Delivery", processadorAprovado, catalogo, cliente));
        assertEquals("ERR01 - O pedido referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaModalidadeNulaEmFinalizarPedido() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> HamburgueriaFacade.finalizarPedido(pedidoComLanche, null, processadorAprovado, catalogo, cliente));
        assertEquals("ERR02 - A modalidade inserida não pode ser nula ou em branco!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaProcessadorNuloEmFinalizarPedido() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> HamburgueriaFacade.finalizarPedido(pedidoComLanche, "Delivery",
                        null, catalogo, cliente));
        assertEquals("ERR01 - O processador de pagamento referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCatalogoNuloEmFinalizarPedido() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> HamburgueriaFacade.finalizarPedido(pedidoComLanche, "Delivery",
                        processadorAprovado, null, cliente));
        assertEquals("ERR01 - O catálogo de receitas referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaObserverNuloEmFinalizarPedido() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> HamburgueriaFacade.finalizarPedido(pedidoComLanche, "Delivery",
                        processadorAprovado, catalogo, null));
        assertEquals("ERR01 - O observador referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaPedidoForaDoEstadoRecebido() {
        pedidoComLanche.confirmarPreparo();

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> HamburgueriaFacade.finalizarPedido(pedidoComLanche, "Delivery",
                        processadorAprovado, catalogo, cliente));
        assertEquals("ERR07 - O pedido deve se encontrar no estado 'Recebido' para ser finalizado!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaPedidoNuloEmSolicitarDesconto() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> HamburgueriaFacade.solicitarDesconto(null, 10.0f));
        assertEquals("ERR01 - O pedido referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaListaNulaEmConsultarRelatorio() {
        Gerente gerente = new Gerente(6000.0f);
        gerente.setRegimeContratacao(new RegimeCLT());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> HamburgueriaFacade.consultarRelatorio(null, gerente));
        assertEquals("ERR01 - A lista de pedidos referenciada não pode ser nula!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCargoNuloEmConsultarRelatorio() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedidoComLanche);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> HamburgueriaFacade.consultarRelatorio(pedidos, null));
        assertEquals("ERR01 - O cargo referenciado não pode ser nulo!", e.getMessage());
    }
}
