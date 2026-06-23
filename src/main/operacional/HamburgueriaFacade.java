package main.operacional;

import main.atendimento.CentralAtendimento;
import main.atendimento.CozinhaObserver;
import main.atendimento.ServicoPedidoFactory;
import main.cardapio.CatalogoReceitas;
import main.cardapio.VisitorImpressaoCupom;
import main.funcionario.Cargo;
import main.pagamento.CaixaPagamento;
import main.pagamento.IProcessadorPagamento;
import main.pagamento.StatusPagamento;
import main.pedido.AppClienteObserver;
import main.pedido.EstadoRecebido;
import main.pedido.Pedido;
import java.util.List;

public class HamburgueriaFacade {

    public static List<String> finalizarPedido(Pedido pedido, String modalidade,
                                               IProcessadorPagamento processadorPagamento,
                                               CatalogoReceitas catalogoReceitas,
                                               AppClienteObserver appClienteObserver) {
        if (pedido == null) {
            throw new IllegalArgumentException("ERR01 - O pedido referenciado não pode ser nulo!");
        }
        if (modalidade == null || modalidade.isBlank()) {
            throw new IllegalArgumentException("ERR02 - A modalidade inserida não pode ser nula ou em branco!");
        }
        if (processadorPagamento == null) {
            throw new IllegalArgumentException("ERR01 - O processador de pagamento referenciado não pode ser nulo!");
        }
        if (catalogoReceitas == null) {
            throw new IllegalArgumentException("ERR01 - O catálogo de receitas referenciado não pode ser nulo!");
        }
        if (appClienteObserver == null) {
            throw new IllegalArgumentException("ERR01 - O observador referenciado não pode ser nulo!");
        }
        if (pedido.getEstado() != EstadoRecebido.getInstance()) {
            throw new IllegalStateException("ERR07 - O pedido deve se encontrar no estado 'Recebido'" +
                    " para ser finalizado!");
        }
        CozinhaObserver cozinhaObserver = new CozinhaObserver(catalogoReceitas);
        CentralAtendimento.getInstance().acompanharPedido(pedido, appClienteObserver);
        CentralAtendimento.getInstance().acompanharPedido(pedido, cozinhaObserver);
        pedido.confirmarPreparo();
        pedido.finalizarPreparo();
        StatusPagamento status = new CaixaPagamento(processadorPagamento)
                .processarPagamento(pedido.getValorTotal());
        if (status != StatusPagamento.APROVADO) {
            pedido.cancelar();
            return null;
        }
        List<String> cupom = new VisitorImpressaoCupom().imprimirCupom(pedido);
        ServicoPedidoFactory.obterServico(modalidade).iniciar(pedido);
        return cupom;
    }

    public static String solicitarDesconto(Pedido pedido, float percentual) {
        if (pedido == null) {
            throw new IllegalArgumentException("ERR01 - O pedido referenciado não pode ser nulo!");
        }
        GerenteAprovador gerente = new GerenteAprovador();
        SupervisorAprovador supervisor = new SupervisorAprovador();
        AtendenteAprovador atendente = new AtendenteAprovador();
        supervisor.setSuperior(gerente);
        atendente.setSuperior(supervisor);
        SolicitacaoDesconto solicitacao = new SolicitacaoDesconto(pedido, percentual);
        return atendente.aprovarDesconto(solicitacao);
    }

    public static List<String> consultarRelatorio(List<Pedido> pedidos, Cargo cargo) {
        if (pedidos == null) {
            throw new IllegalArgumentException("ERR01 - A lista de pedidos referenciada não pode ser nula!");
        }
        if (cargo == null) {
            throw new IllegalArgumentException("ERR01 - O cargo referenciado não pode ser nulo!");
        }
        return new RelatorioFaturamentoProxy(pedidos, cargo).getDadosRelatorio();
    }
}