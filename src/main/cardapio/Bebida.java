package main.cardapio;

public class Bebida extends ItemCardapio {

    private final float preco;
    private final int calorias;

    public Bebida(String descricao, float preco, int calorias) {
        super(descricao);
        if (preco < 0) {
            throw new IllegalArgumentException("ERR03 - O preço da bebida não pode ser negativo!");
        }
        if (calorias < 0) {
            throw new IllegalArgumentException("ERR03 - A quantidade de calorias da bebida não pode ser negativa!");
        }
        this.preco = preco;
        this.calorias = calorias;
    }

    public float getPreco() {
        return preco;
    }

    public int getCalorias() {
        return calorias;
    }

    @Override
    public String aceitar(VisitorItemCardapio visitor) {
        return visitor.visitarBebida(this);
    }

    @Override
    public String toString() {
        return "Bebida{" +
                "Descrição'" + getDescricao() + '\'' +
                ", Preço:" + preco +
                ", Quantidade de Calorias:" + calorias +
                '}';
    }
}
