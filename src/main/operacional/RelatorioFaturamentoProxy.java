package main.operacional;

import main.funcionario.Atendente;
import main.funcionario.Cargo;
import main.funcionario.Gerente;
import main.funcionario.Supervisor;
import main.pedido.Pedido;

import java.util.List;

public class RelatorioFaturamentoProxy implements IRelatorioFaturamento {

    private final Cargo cargo;
    private final List<Pedido> pedidos;
    private IRelatorioFaturamento relatorio;

    public RelatorioFaturamentoProxy(List<Pedido> pedidos, Cargo cargo) {
        if (pedidos == null) {
            throw new IllegalArgumentException("ERR01 - A lista de pedidos referenciada não pode ser nula!");
        }
        if (cargo == null) {
            throw new IllegalArgumentException("ERR01 - O cargo referenciado não pode ser nulo!");
        }
        this.pedidos = pedidos;
        this.cargo = cargo;
    }

    public List<String> getDadosRelatorio() {
        if (this.relatorio == null) {
            switch (this.cargo) {
                case Gerente gerente -> this.relatorio = new RelatorioGerente(this.pedidos);
                case Supervisor supervisor -> this.relatorio = new RelatorioSupervisor(this.pedidos);
                case Atendente atendente -> this.relatorio = new RelatorioAtendente(this.pedidos);
                default -> throw new IllegalArgumentException("ERR11 - Nível de Autoridade Insuficiente Para" +
                        " Acessar Este Relatório!");
            }
        }
        return this.relatorio.getDadosRelatorio();
    }
}
