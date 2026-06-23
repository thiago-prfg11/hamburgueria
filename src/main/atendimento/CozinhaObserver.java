package main.atendimento;

import main.cardapio.CatalogoReceitas;
import main.cozinha.CozinhaPedido;
import main.cozinha.PainelCozinha;
import main.pedido.EstadoCancelado;
import main.pedido.EstadoEmPreparo;
import main.pedido.Pedido;
import java.util.ArrayList;
import java.util.List;

public class CozinhaObserver implements IObserver {

    private final PainelCozinha painelCozinha;
    private final List<String> pedidosEmPreparo;

    public CozinhaObserver(CatalogoReceitas catalogoReceitas) {
        if (catalogoReceitas == null) {
            throw new IllegalArgumentException("ERR01 - O catálogo de receitas referenciado não pode ser nulo!");
        }
        this.painelCozinha = new PainelCozinha(new CozinhaPedido(catalogoReceitas));
        this.pedidosEmPreparo = new ArrayList<>();
    }

    public void notificar(Pedido pedido) {
        if (pedido == null) {
            return;
        }
        if (pedido.getEstado() == EstadoEmPreparo.getInstance()) {
            this.painelCozinha.receberPedido(pedido);
            this.pedidosEmPreparo.add(pedido.getCodigoPedido());
            int quantidade = this.painelCozinha.getFilaEspera().size();
            for (int i = 0; i < quantidade; i++) {
                this.painelCozinha.executarProximaTarefa();
            }
            return;
        }
        if (pedido.getEstado() == EstadoCancelado.getInstance() && this.pedidosEmPreparo.contains(pedido.getCodigoPedido())) {
            this.painelCozinha.desfazerUltimaTarefa();
            this.pedidosEmPreparo.remove(pedido.getCodigoPedido());
        }
    }

    public PainelCozinha getPainelCozinha() {
        return painelCozinha;
    }
}
