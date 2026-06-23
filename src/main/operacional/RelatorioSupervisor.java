package main.operacional;

import main.pedido.Pedido;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RelatorioSupervisor implements IRelatorioFaturamento {

    private List<Pedido> pedidos;

    public RelatorioSupervisor(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<String> getDadosRelatorio() {
        float faturamentoTotal = 0;
        for (Pedido pedido : this.pedidos) {
            faturamentoTotal += pedido.getValorTotal();
        }
        List<String> dados = new ArrayList<String>();
        dados.add("Número de Pedidos Atendidos: " + this.pedidos.size());
        dados.add("Faturamento Total (Em Reais): R$" + String.format(Locale.US, "%.2f", faturamentoTotal));
        return dados;
    }
}
