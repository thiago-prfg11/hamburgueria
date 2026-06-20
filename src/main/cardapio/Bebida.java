package main.cardapio;

public class Bebida extends ItemCardapio {

    private final float preco;
    private final int calorias;

    public Bebida(String descricao, float preco, int calorias) {
        super(descricao);
        if (preco < 0) {
            throw new IllegalArgumentException("O preço da bebida é inválido!");
        }
        if (calorias < 0) {
            throw new IllegalArgumentException("A quantidade de calorias da bebida é inválida!");
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
        return "Bebida{" +
                "Descrição:'" + getDescricao() + '\'' +
                ", Preço:" + preco +
                ", Quantidade de Calorias:" + calorias +
                '}';
    }
}
