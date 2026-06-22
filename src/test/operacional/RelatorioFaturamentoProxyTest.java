package test.operacional;

import main.cardapio.Bebida;
import main.funcionario.*;
import main.operacional.IRelatorioFaturamento;
import main.operacional.RelatorioFaturamentoProxy;
import main.pedido.FreteFixo;
import main.pedido.Pedido;
import main.pedido.PedidoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioFaturamentoProxyTest {

    List<Pedido> pedidos;
    Atendente atendente;
    Supervisor supervisor;
    Gerente gerente;

    @BeforeEach
    void setUp() {
        atendente = new Atendente(1500.0f);
        atendente.setRegimeContratacao(new RegimeCLT());

        supervisor = new Supervisor(3000.0f);
        supervisor.setRegimeContratacao(new RegimeCLT());

        gerente = new Gerente(6000.0f);
        gerente.setRegimeContratacao(new RegimeCLT());

        pedidos = new ArrayList<>();
        Pedido pedido1 = new PedidoBuilder()
                .setCodigoPedido("PED-PROXY-001")
                .setNomeCliente("Cliente A")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Refrigerante Proxy", 6.0f, 140))
                .build();
        Pedido pedido2 = new PedidoBuilder()
                .setCodigoPedido("PED-PROXY-002")
                .setNomeCliente("Cliente B")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Suco Proxy", 8.0f, 90))
                .build();
        pedidos.add(pedido1);
        pedidos.add(pedido2);
    }

    @Test
    void deveRetornarTotalDePedidosParaAtendente() {
        IRelatorioFaturamento relatorio = new RelatorioFaturamentoProxy(pedidos, atendente);

        assertEquals("Número de Pedidos Atendidos: 2", relatorio.getDadosRelatorio().getFirst());
    }

    @Test
    void deveRetornarTotalDePedidosParaSupervisor() {
        IRelatorioFaturamento relatorio = new RelatorioFaturamentoProxy(pedidos, supervisor);

        assertEquals("Número de Pedidos Atendidos: 2", relatorio.getDadosRelatorio().getFirst());
    }

    @Test
    void deveRetornarTotalDePedidosParaGerente() {
        IRelatorioFaturamento relatorio = new RelatorioFaturamentoProxy(pedidos, gerente);

        assertEquals("Número de Pedidos Atendidos: 2", relatorio.getDadosRelatorio().getFirst());
    }

    @Test
    void deveRetornarFaturamentoTotalParaSupervisor() {
        IRelatorioFaturamento relatorio = new RelatorioFaturamentoProxy(pedidos, supervisor);

        float freteFixo = pedidos.getFirst().getValorFrete();
        float totalEsperado = 6.0f + 8.0f + (freteFixo * 2);
        assertEquals("Faturamento Total (Em Reais): R$" + String.format(Locale.US, "%.2f", totalEsperado),
                relatorio.getDadosRelatorio().get(1));
    }

    @Test
    void deveRetornarFaturamentoTotalParaGerente() {
        IRelatorioFaturamento relatorio = new RelatorioFaturamentoProxy(pedidos, gerente);

        float freteFixo = pedidos.get(0).getValorFrete();
        float totalEsperado = 6.0f + 8.0f + (freteFixo * 2);
        assertEquals("Faturamento Total (Em Reais): R$" + String.format(Locale.US, "%.2f", totalEsperado),
                relatorio.getDadosRelatorio().get(1));
    }

    @Test
    void deveRetornarTicketMedioCorretoParaGerente() {
        IRelatorioFaturamento relatorio = new RelatorioFaturamentoProxy(pedidos, gerente);

        float freteFixo = pedidos.get(0).getValorFrete();
        float totalEsperado = 6.0f + 8.0f + (freteFixo * 2);
        float ticketEsperado = totalEsperado / 2;
        assertEquals("Ticket Médio (Em Reais): R$" + String.format(Locale.US, "%.2f", ticketEsperado),
                relatorio.getDadosRelatorio().get(2));
    }

    @Test
    void deveRetornarExcecaoParaCargoNaoAutorizado() {
        Cargo cargoNaoAutorizado = new Cargo(0.0f) {
            public float calcularSalario() { return 0.0f; }
        };
        IRelatorioFaturamento relatorio = new RelatorioFaturamentoProxy(pedidos, cargoNaoAutorizado);

        try {
            relatorio.getDadosRelatorio();
        } catch (IllegalArgumentException e) {
            assertEquals("Nível de Autoridade Insuficiente Para Acessar Este Relatório!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaListaDePedidosNula() {
        try {
            new RelatorioFaturamentoProxy(null, gerente);
        } catch (IllegalArgumentException e) {
            assertEquals("A lista de pedidos referenciada não pode ser nula!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCargoNulo() {
        try {
            new RelatorioFaturamentoProxy(pedidos, null);
        } catch (IllegalArgumentException e) {
            assertEquals("O cargo referenciado não pode ser nulo!", e.getMessage());
        }
    }
}
