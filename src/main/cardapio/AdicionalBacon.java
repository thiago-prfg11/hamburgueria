package main.cardapio;

public class AdicionalBacon extends LancheDecorator {

    public AdicionalBacon(ItemCardapio itemDecorado) {
        super(itemDecorado);
    }

    public float getPrecoAdicional() {
        return 4.50f;
    }

    public int getCaloriasAdicional() {
        return 90;
    }

    public String getNomeAdicional() {
        return "Bacon";
    }
}
