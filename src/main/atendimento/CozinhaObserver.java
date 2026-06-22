package main.atendimento;

import main.cardapio.CatalogoReceitas;
import main.cozinha.CozinhaPedido;
import main.cozinha.PainelCozinha;
import main.pedido.Pedido;
import java.util.ArrayList;
import java.util.List;

public class CozinhaObserver implements IObserver {

    private final PainelCozinha painelCozinha;
    private final List<String> pedidosEmPreparo;

    public CozinhaObserver(CatalogoReceitas catalogoReceitas) {
        if (catalogoReceitas == null) {
            throw new IllegalArgumentException("O catálogo de receitas referenciado não pode ser nulo!");
        }
        this.painelCozinha = new PainelCozinha(new CozinhaPedido(catalogoReceitas));
        this.pedidosEmPreparo = new ArrayList<String>();
    }

    public void notificar(Pedido pedido) {
        if (pedido == null) {
            return;
        }
        String estado = pedido.getDescricaoEstado();
        if (estado.equals("Em Preparo")) {
            this.painelCozinha.receberPedido(pedido);
            this.pedidosEmPreparo.add(pedido.getCodigoPedido());
            return;
        }
        if (estado.equals("Cancelado") && this.pedidosEmPreparo.contains(pedido.getCodigoPedido())) {
            this.painelCozinha.desfazerUltimaTarefa();
            this.pedidosEmPreparo.remove(pedido.getCodigoPedido());
        }
    }

    public PainelCozinha getPainelCozinha() {
        return painelCozinha;
    }
}
