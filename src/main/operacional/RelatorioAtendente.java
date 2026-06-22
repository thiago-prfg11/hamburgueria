package main.operacional;

import main.pedido.Pedido;

import java.util.ArrayList;
import java.util.List;

public class RelatorioAtendente implements IRelatorioFaturamento {

    private final List<Pedido> pedidos;

    public RelatorioAtendente(List<Pedido> pedidos) {
        if (pedidos == null) {
            throw new IllegalArgumentException("A lista de pedidos referenciada não pode ser nula!");
        }
        this.pedidos = pedidos;
    }

    public List<String> getDadosRelatorio() {
        List<String> dados = new ArrayList<String>();
        dados.add("Número de Pedidos Atendidos: " + this.pedidos.size());
        return dados;
    }
}
