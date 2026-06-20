package main.cardapio;

public class AdicionalOvo extends LancheDecorator {

    public AdicionalOvo(ItemCardapio itemDecorado) {
        super(itemDecorado);
    }

    public float getPrecoAdicional() {
        return 2.50f;
    }

    public int getCaloriasAdicional() {
        return 70;
    }

    public String getNomeAdicional() {
        return "Ovo";
    }
}
