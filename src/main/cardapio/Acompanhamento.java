package main.cardapio;

public class Acompanhamento extends ItemCardapio {

    private final float preco;
    private final int calorias;

    public Acompanhamento(String descricao, float preco, int calorias) {
        super(descricao);
        if (preco < 0) {
            throw new IllegalArgumentException("O preço do acompanhamento é inválido!");
        }
        if (calorias < 0) {
            throw new IllegalArgumentException("A quantidade de calorias do acompanhamento é inválido!");
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
    public String toString() {
        return "Acompanhamento{" +
                "Descrição:'" + getDescricao() + '\'' +
                ", Preço:" + preco +
                ", Quantidade de Calorias:" + calorias +
                '}';
    }
}
