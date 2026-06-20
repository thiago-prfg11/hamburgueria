package main.cardapio;

import java.util.Iterator;

public class RelatorioCardapio {

    public static Integer contarItensDisponiveis(Cardapio cardapio) {
        int quantidade = 0;
        for (ItemCardapio item : cardapio) {
            if (item.isDisponivel()) {
                quantidade++;
            }
        }
        return quantidade;
    }

    public static Integer contarTotalItens(Cardapio cardapio) {
        int quantidade = 0;
        for (Iterator<ItemCardapio> i = cardapio.iterator(); i.hasNext(); ) {
            quantidade++;
            i.next();
        }
        return quantidade;
    }

}
