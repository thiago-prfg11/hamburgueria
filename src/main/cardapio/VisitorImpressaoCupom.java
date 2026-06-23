package main.cardapio;

import main.pedido.Pedido;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VisitorImpressaoCupom implements VisitorItemCardapio {

    public String imprimir(ItemCardapio item) {
        return item.aceitar(this);
    }

    public List<String> imprimirCupom(Pedido pedido) {
        List<String> cupom = new ArrayList<String>();
        cupom.add("--- Cupom Fiscal ---");
        cupom.add("Pedido: " + pedido.getCodigoPedido());
        for (ItemCardapio item : pedido.getItens()) {
            cupom.add(item.aceitar(this));
        }
        cupom.add("Total: R$ " + String.format(Locale.US, "%.2f", pedido.getValorTotal()));
        cupom.add("--------------------");
        return cupom;
    }

    public String visitarLanche(ItemCardapio lanche) {
        return formatarLinha(lanche);
    }

    public String visitarBebida(Bebida bebida) {
        return formatarLinha(bebida);
    }

    public String visitarAcompanhamento(Acompanhamento acompanhamento) {
        return formatarLinha(acompanhamento);
    }

    public String visitarCombo(Combo combo) {
        return formatarLinha(combo);
    }

    private String formatarLinha(ItemCardapio item) {
        return item.getDescricao() + " ... R$ " + String.format(Locale.US, "%.2f", item.getPreco());
    }
}