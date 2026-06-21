package main.atendimento;

public class FabricaEmbalagemBalcao implements FabricaEmbalagem {

    public Embalagem createEmbalagem() {
        return new Bandeja();
    }

    public ItemProtecao createItemProtecao() {
        return new Guardanapo();
    }
}
