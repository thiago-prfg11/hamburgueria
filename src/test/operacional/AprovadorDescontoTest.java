package test.operacional;

import main.cardapio.Bebida;
import main.operacional.AtendenteAprovador;
import main.operacional.GerenteAprovador;
import main.operacional.SolicitacaoDesconto;
import main.operacional.SupervisorAprovador;
import main.pedido.FreteFixo;
import main.pedido.Pedido;
import main.pedido.PedidoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AprovadorDescontoTest {

    AtendenteAprovador atendente;
    SupervisorAprovador supervisor;
    GerenteAprovador gerente;
    Pedido pedido;

    @BeforeEach
    void setUp() {
        gerente = new GerenteAprovador();
        supervisor = new SupervisorAprovador();
        supervisor.setSuperior(gerente);
        atendente = new AtendenteAprovador();
        atendente.setSuperior(supervisor);

        pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CHAIN-001")
                .setNomeCliente("Cliente Chain")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Refrigerante Chain", 10.0f, 140))
                .build();
    }

    @Test
    void deveAtendenteAprovarDescontoAbaixoDoSeuLimite() {
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, 5.0f);
        String resultado = atendente.aprovarDesconto(solicitacao);
        assertEquals("Atendente", resultado);
    }

    @Test
    void deveSupervisorAprovarDescontoAcimaDoLimiteDoAtendente() {
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, 10.0f);
        String resultado = atendente.aprovarDesconto(solicitacao);
        assertEquals("Supervisor", resultado);
    }

    @Test
    void deveGerenteAprovarDescontoAcimaDoLimiteDoSupervisor() {
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, 25.0f);
        String resultado = atendente.aprovarDesconto(solicitacao);
        assertEquals("Gerente", resultado);
    }

    @Test
    void deveRecusarDescontoAcimaDoLimiteDoGerente() {
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, 31.0f);
        String resultado = atendente.aprovarDesconto(solicitacao);
        assertEquals("Pedido de Desconto Negado!", resultado);
    }

    @Test
    void deveAplicarDescontoNoPedidoQuandoAprovado() {
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, 5.0f);
        atendente.aprovarDesconto(solicitacao);
        assertEquals(5.0f, pedido.getPercentualDesconto());
    }

    @Test
    void deveCalcularValorTotalComDescontoAplicado() {
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, 10.0f);
        atendente.aprovarDesconto(solicitacao);
        assertEquals(9.0f + pedido.getValorFrete(), pedido.getValorTotal(), 0.01f);
    }

    @Test
    void naoDeveAplicarDescontoNoPedidoQuandoNaoAutorizado() {
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, 31.0f);
        atendente.aprovarDesconto(solicitacao);
        assertEquals(0.0f, pedido.getPercentualDesconto());
    }

    @Test
    void deveManterValorOriginalQuandoDescontoNaoAutorizado() {
        float valorOriginal = pedido.getValorTotal();
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, 31.0f);
        atendente.aprovarDesconto(solicitacao);
        assertEquals(valorOriginal, pedido.getValorTotal(), 0.01f);
    }

    @Test
    void deveGerenteAprovarDescontoNoSeuLimiteMaximo() {
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, 30.0f);
        String resultado = atendente.aprovarDesconto(solicitacao);
        assertEquals("Gerente", resultado);
    }

    @Test
    void deveAtendenteAprovarDescontoZero() {
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, 0.0f);
        String resultado = atendente.aprovarDesconto(solicitacao);
        assertEquals("Atendente", resultado);
    }

    @Test
    void deveRetornarExcecaoParaPedidoNuloNaSolicitacao() {
        try {
            new SolicitacaoDesconto(null, 10.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O pedido referenciado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaPercentualNegativoNaSolicitacao() {
        try {
            new SolicitacaoDesconto(pedido, -1.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O percentual de desconto não pode ser negativo ou maior que 100!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaPercentualAcimaDe100NaSolicitacao() {
        try {
            new SolicitacaoDesconto(pedido, 101.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("O percentual de desconto não pode ser negativo ou maior que 100!", e.getMessage());
        }
    }
}
