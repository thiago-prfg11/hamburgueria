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
        ingredientes.add(IngredienteFactory.getIngrediente("Pao Facade", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Carne Facade", 280, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Facade", ingredientes, 22.90f,
                12, TecnicaPreparo.TRADICIONAL));

        cliente = new AppClienteObserver("Cliente Facade");
        processadorAprovado = new AdaptadorStone();
        processadorRecusado = new AdaptadorGertec();

        pedidoComLanche = new PedidoBuilder()
                .setCodigoPedido("PED-FACADE-001")
                .setNomeCliente("Cliente Facade")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Facade")))
                .build();

        pedidoSomenteComBebida = new PedidoBuilder()
                .setCodigoPedido("PED-FACADE-002")
                .setNomeCliente("Cliente Facade")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Refrigerante Facade", 6.0f, 140))
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

        assertEquals("Pedido: PED-FACADE-001", cupom.get(1));
    }

    @Test
    void deveRetornarCupomComItemNoFormatoDoVisitor() {
        List<String> cupom = HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        assertEquals("X-Facade ... R$ 22.90", cupom.get(2));
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

        List<String> historico = CentralAtendimento.getInstance().getHistoricoPedido("PED-FACADE-001");
        assertEquals("Pedido PED-FACADE-001 está: Em Preparo", historico.getFirst());
    }

    @Test
    void deveRegistrarProntoNoHistorico() {
        HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        List<String> historico = CentralAtendimento.getInstance().getHistoricoPedido("PED-FACADE-001");
        assertEquals("Pedido PED-FACADE-001 está: Pronto", historico.get(1));
    }

    @Test
    void deveRegistrarSaiuParaEntregaNoHistorico() {
        HamburgueriaFacade.finalizarPedido(
                pedidoComLanche, "Delivery", processadorAprovado, catalogo, cliente);

        List<String> historico = CentralAtendimento.getInstance().getHistoricoPedido("PED-FACADE-001");
        assertEquals("Pedido PED-FACADE-001 está: Saiu para Entrega", historico.get(2));
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

        assertEquals("Atenção, Cliente Facade, seu pedido PED-FACADE-001 se encontra no seguinte estado:" +
                        " Saiu para Entrega",
                cliente.getUltimaNotificacao());
    }

    @Test
    void deveRetornarCupomNullQuandoPagamentoForRecusado() {
        List<Ingrediente> ingredientesCaros = new ArrayList<>();
        ingredientesCaros.add(IngredienteFactory.getIngrediente("Pao Gourmet Facade", 150, false));
        ingredientesCaros.add(IngredienteFactory.getIngrediente("Wagyu Facade", 400, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Wagyu Facade", ingredientesCaros, 200.0f,
                20, TecnicaPreparo.TRADICIONAL));

        Pedido pedidoCaro = new PedidoBuilder()
                .setCodigoPedido("PED-FACADE-003")
                .setNomeCliente("Cliente Facade")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Wagyu Facade")))
                .build();

        List<String> cupom = HamburgueriaFacade.finalizarPedido(
                pedidoCaro, "Delivery", processadorRecusado, catalogo, cliente);

        assertNull(cupom);
    }

    @Test
    void deveCancelarPedidoQuandoPagamentoForRecusado() {
        List<Ingrediente> ingredientesCaros = new ArrayList<>();
        ingredientesCaros.add(IngredienteFactory.getIngrediente("Pao Premium Facade", 150, false));
        ingredientesCaros.add(IngredienteFactory.getIngrediente("Picanha Facade", 400, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Picanha Facade", ingredientesCaros, 200.0f,
                20, TecnicaPreparo.TRADICIONAL));

        Pedido pedidoCaro = new PedidoBuilder()
                .setCodigoPedido("PED-FACADE-004")
                .setNomeCliente("Cliente Facade")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Picanha Facade")))
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
        try {
            HamburgueriaFacade.finalizarPedido(null, "Delivery", processadorAprovado, catalogo, cliente);
        } catch (IllegalArgumentException e) {
            assertEquals("O pedido referenciado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaModalidadeNulaEmFinalizarPedido() {
        try {
            HamburgueriaFacade.finalizarPedido(pedidoComLanche, null, processadorAprovado, catalogo, cliente);
        } catch (IllegalArgumentException e) {
            assertEquals("A modalidade inserida não pode ser nula!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaProcessadorNuloEmFinalizarPedido() {
        try {
            HamburgueriaFacade.finalizarPedido(pedidoComLanche, "Delivery",
                    null, catalogo, cliente);
        } catch (IllegalArgumentException e) {
            assertEquals("O processador de pagamento referenciado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCatalogoNuloEmFinalizarPedido() {
        try {
            HamburgueriaFacade.finalizarPedido(pedidoComLanche, "Delivery",
                    processadorAprovado, null, cliente);
        } catch (IllegalArgumentException e) {
            assertEquals("O catálogo de receitas referenciado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaObserverNuloEmFinalizarPedido() {
        try {
            HamburgueriaFacade.finalizarPedido(pedidoComLanche, "Delivery",
                    processadorAprovado, catalogo, null);
        } catch (IllegalArgumentException e) {
            assertEquals("O observer referenciado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaPedidoForaDoEstadoRecebido() {
        pedidoComLanche.confirmarPreparo();

        try {
            HamburgueriaFacade.finalizarPedido(pedidoComLanche, "Delivery",
                    processadorAprovado, catalogo, cliente);
        } catch (IllegalStateException e) {
            assertEquals("O pedido deve se encontrar no estado 'Recebido' para ser finalizado!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaPedidoNuloEmSolicitarDesconto() {
        try {
            HamburgueriaFacade.solicitarDesconto(null, 10.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O pedido referenciado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaListaNulaEmConsultarRelatorio() {
        Gerente gerente = new Gerente(6000.0f);
        gerente.setRegimeContratacao(new RegimeCLT());

        try {
            HamburgueriaFacade.consultarRelatorio(null, gerente);
        } catch (IllegalArgumentException e) {
            assertEquals("A lista de pedidos referenciada não pode ser nula!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCargoNuloEmConsultarRelatorio() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedidoComLanche);

        try {
            HamburgueriaFacade.consultarRelatorio(pedidos, null);
        } catch (IllegalArgumentException e) {
            assertEquals("O cargo referenciado não pode ser nulo!", e.getMessage());
        }
    }
}
