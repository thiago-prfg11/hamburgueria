package main.cardapio;

public abstract class ItemCardapio {

    private String descricao;
    private boolean disponivel = true;

    public ItemCardapio(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição do item não pode ser nula ou vazia!");
        }
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição do item não pode ser nula ou vazia!");
        }
        this.descricao = descricao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public abstract float getPreco();

    public abstract int getCalorias();

    public abstract String aceitar(VisitorItemCardapio visitor);
}
