package main.cardapio;

public abstract class ItemCardapio {

    private String descricao;

    public ItemCardapio(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição do item é inválida!");
        }
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição inserida para o item é inválida!");
        }
        this.descricao = descricao;
    }

    public abstract float getPreco();

    public abstract int getCalorias();
}
