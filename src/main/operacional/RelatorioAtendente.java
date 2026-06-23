package main.operacional;

import main.pedido.Pedido;
import java.util.ArrayList;
import java.util.List;

public class RelatorioAtendente implements IRelatorioFaturamento {

    private List<Pedido> pedidos;

    public RelatorioAtendente(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<String> getDadosRelatorio() {
        List<String> dados = new ArrayList<String>();
        dados.add("Número de Pedidos Atendidos: " + this.pedidos.size());
        return dados;
    }
}