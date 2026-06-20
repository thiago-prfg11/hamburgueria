package main.cardapio;

public abstract class LancheDecorator extends ItemCardapio {

    private final ItemCardapio itemDecorado;

    public LancheDecorator(ItemCardapio itemDecorado) {
        super(extrairDescricao(itemDecorado));
        if (!(itemDecorado instanceof Lanche) && !(itemDecorado instanceof LancheDecorator)) {
            throw new IllegalArgumentException("Somente lanches podem receber itens adicionais!");
        }
        this.itemDecorado = itemDecorado;
    }

    private static String extrairDescricao(ItemCardapio itemDecorado) {
        if (itemDecorado == null) {
            throw new IllegalArgumentException("O item buscado não pode ser nulo!");
        }
        return itemDecorado.getDescricao();
    }

    public ItemCardapio getItemDecorado() {
        return itemDecorado;
    }

    public abstract float getPrecoAdicional();

    public abstract int getCaloriasAdicional();

    public abstract String getNomeAdicional();

    public float getPreco() {
        return this.itemDecorado.getPreco() + this.getPrecoAdicional();
    }

    public int getCalorias() {
        return this.itemDecorado.getCalorias() + this.getCaloriasAdicional();
    }

    @Override
    public String getDescricao() {
        return this.itemDecorado.getDescricao() + " + " + this.getNomeAdicional();
    }

    @Override
    public void setDescricao(String descricao) {
        throw new UnsupportedOperationException("A descrição de um item decorado é derivada automaticamente," +
                " não podendo ser alterada!");
    }

    @Override
    public String aceitar(VisitorItemCardapio visitor) {
        return visitor.visitarLanche(this);
    }
}