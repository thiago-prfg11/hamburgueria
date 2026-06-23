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
                .setCodigoPedido("PED-001")
                .setNomeCliente("Agostinho Carrara")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Pepsi – Lata 350 ml", 6.0f, 140))
                .build();

        Pedido pedido2 = new PedidoBuilder()
                .setCodigoPedido("PED-002")
                .setNomeCliente("Paulo Wilson")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Maguary – Suco de Laranja", 8.0f, 90))
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

        float freteFixo = pedidos.getFirst().getValorFrete();
        float totalEsperado = 6.0f + 8.0f + (freteFixo * 2);
        assertEquals("Faturamento Total (Em Reais): R$" + String.format(Locale.US, "%.2f", totalEsperado),
                relatorio.getDadosRelatorio().get(1));
    }

    @Test
    void deveRetornarTicketMedioCorretoParaGerente() {
        IRelatorioFaturamento relatorio = new RelatorioFaturamentoProxy(pedidos, gerente);

        float freteFixo = pedidos.getFirst().getValorFrete();
        float totalEsperado = 6.0f + 8.0f + (freteFixo * 2);
        float ticketEsperado = totalEsperado / 2;
        assertEquals("Ticket Médio (Em Reais): R$" + String.format(Locale.US, "%.2f", ticketEsperado),
                relatorio.getDadosRelatorio().get(2));
    }

    @Test
    void deveReutilizarRelatorioInstanciadoPreviamente() {
        IRelatorioFaturamento relatorio = new RelatorioFaturamentoProxy(pedidos, gerente);

        List<String> primeiraConsulta = relatorio.getDadosRelatorio();
        List<String> segundaConsulta = relatorio.getDadosRelatorio();

        assertEquals(primeiraConsulta, segundaConsulta);
    }

    @Test
    void deveRetornarExcecaoParaListaDePedidosNula() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new RelatorioFaturamentoProxy(null, gerente));
        assertEquals("ERR01 - A lista de pedidos referenciada não pode ser nula!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCargoNulo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new RelatorioFaturamentoProxy(pedidos, null));
        assertEquals("ERR01 - O cargo referenciado não pode ser nulo!", e.getMessage());
    }
}