package main.cardapio;

public interface VisitorItemCardapio {

    String visitarLanche(ItemCardapio lanche);
    String visitarBebida(Bebida bebida);
    String visitarAcompanhamento(Acompanhamento acompanhamento);
    String visitarCombo(Combo combo);

}
