package main.cardapio;

public class VisitorTabelaNutricional implements VisitorItemCardapio {

    public String exibir(ItemCardapio item) {
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
        return "Lanche: " + item.getDescricao() + " | Calorias: " + item.getCalorias() + "KCal";
    }
}
