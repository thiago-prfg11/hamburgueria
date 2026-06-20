package main.cardapio;

public class Acompanhamento extends ItemCardapio {

    private float preco;
    private int calorias;

    public Acompanhamento(String descricao, float preco, int calorias) {
        super(descricao);
        if (preco < 0) {
            throw new IllegalArgumentException("O preço do acompanhamento não pode ser negativo!");
        }
        if (calorias < 0) {
            throw new IllegalArgumentException("A quantidade de calorias não pode ser negativa!");
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
        return visitor.visitarAcompanhamento(this);
    }

    @Override
    public String toString() {
        return "Acompanhamento{" +
                "Descrição:'" + getDescricao() + '\'' +
                ", Preço:" + preco +
                ", Calorias:" + calorias +
                '}';
    }
}
