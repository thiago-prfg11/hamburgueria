package main.cardapio;

import java.util.Locale;

public class VisitorImpressaoCupom implements VisitorItemCardapio {

    public String imprimir(ItemCardapio item) {
        return item.aceitar(this);
    }

    @Override
    public String visitarLanche(ItemCardapio lanche) {
        return formatarLinha(lanche);
    }

    @Override
    public String visitarBebida(Bebida bebida) {
        return formatarLinha(bebida);
    }

    @Override
    public String visitarAcompanhamento(Acompanhamento acompanhamento) {
        return formatarLinha(acompanhamento);
    }

    @Override
    public String visitarCombo(Combo combo) {
        return formatarLinha(combo);
    }

    private String formatarLinha(ItemCardapio item) {
        return "Lanche: " + item.getDescricao() + " | Valor: R$" + String.format(Locale.US, "%.2f", item.getPreco());
    }
}
