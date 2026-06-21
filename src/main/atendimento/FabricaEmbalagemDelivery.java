package main.atendimento;

public class FabricaEmbalagemDelivery implements FabricaEmbalagem {

    public Embalagem createEmbalagem() {
        return new SacolaTermica();
    }

    public ItemProtecao createItemProtecao() {
        return new LacreSeguranca();
    }
}
