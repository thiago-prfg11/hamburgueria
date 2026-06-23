package main.cardapio;

public class AdicionalCheddarExtra extends LancheDecorator {

    public AdicionalCheddarExtra(ItemCardapio itemDecorado) {
        super(itemDecorado);
    }

    public float getPrecoAdicional() {
        return 3.00f;
    }

    public int getCaloriasAdicional() {
        return 60;
    }

    public String getNomeAdicional() {
        return "Cheddar Extra";
    }
}
