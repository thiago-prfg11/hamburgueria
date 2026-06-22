package main.cozinha;

import main.cardapio.Lanche;

public class InicioPreparoTarefa implements TarefaCozinha {

    private final CozinhaPedido cozinhaPedido;
    private final Lanche lanche;

    public InicioPreparoTarefa(CozinhaPedido cozinhaPedido, Lanche lanche) {
        if (cozinhaPedido == null) {
            throw new IllegalArgumentException("A cozinha referenciada não pode ser nula!");
        }
        if (lanche == null) {
            throw new IllegalArgumentException("O lanche referenciado não pode ser nulo!");
        }
        this.cozinhaPedido = cozinhaPedido;
        this.lanche = lanche;
    }

    public void executar() {
        this.cozinhaPedido.prepararLanche(this.lanche);
    }

    public void desfazer() {
        this.cozinhaPedido.descartarLanche(this.lanche);
    }
}
