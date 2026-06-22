package main.cozinha;

import main.cardapio.ItemCardapio;
import main.cardapio.Lanche;
import main.cardapio.LancheDecorator;
import main.pedido.EstadoEmPreparo;
import main.pedido.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PainelCozinha {

    private final CozinhaPedido cozinhaPedido;
    private final List<TarefaCozinha> filaEspera;
    private final List<TarefaCozinha> historico;

    public PainelCozinha(CozinhaPedido cozinhaPedido) {
        if (cozinhaPedido == null) {
            throw new IllegalArgumentException("A cozinha referenciada não pode ser nula!");
        }
        this.cozinhaPedido = cozinhaPedido;
        this.filaEspera = new ArrayList<TarefaCozinha>();
        this.historico = new ArrayList<TarefaCozinha>();
    }

    public void receberPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("O pedido referenciado não pode ser nulo!");
        }
        if (pedido.getEstado() != EstadoEmPreparo.getInstance()) {
            throw new IllegalStateException("O pedido deve estar com o status 'Em Preparo' para ser enviado à cozinha!");
        }
        for (ItemCardapio item : pedido.getItens()) {
            if (item instanceof Lanche && !(item instanceof LancheDecorator)) {
                this.filaEspera.add(new InicioPreparoTarefa(this.cozinhaPedido, (Lanche) item));
            }
        }
    }

    public void executarProximaTarefa() {
        if (this.filaEspera.isEmpty()) {
            return;
        }
        TarefaCozinha tarefa = this.filaEspera.removeFirst();
        tarefa.executar();
        this.historico.add(tarefa);
    }

    public void desfazerUltimaTarefa() {
        if (this.historico.isEmpty()) {
            return;
        }
        TarefaCozinha tarefa = this.historico.removeLast();
        tarefa.desfazer();
        this.filaEspera.addFirst(tarefa);
    }

    public List<TarefaCozinha> getFilaEspera() {
        return new ArrayList<TarefaCozinha>(this.filaEspera);
    }

    public List<TarefaCozinha> getHistorico() {
        return new ArrayList<TarefaCozinha>(this.historico);
    }
}
