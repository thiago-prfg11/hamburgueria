package main.atendimento;

public class EmbaladorPedido {

    private final Embalagem embalagem;
    private final ItemProtecao itemProtecao;

    public EmbaladorPedido(FabricaEmbalagem fabrica) {
        if (fabrica == null) {
            throw new IllegalArgumentException("ERR01 - A fábrica da embalagem referenciada não pode ser nula!");
        }
        this.embalagem = fabrica.createEmbalagem();
        this.itemProtecao = fabrica.createItemProtecao();
    }

    public String getDescricaoEmbalagem() {
        return this.embalagem.embalar();
    }

    public String getDescricaoProtecao() {
        return this.itemProtecao.aplicar();
    }
}
