package main.cozinha;

import main.cardapio.CatalogoReceitas;
import main.cardapio.Lanche;
import main.cardapio.ReceitaLanche;

public class CozinhaPedido {

    private final CatalogoReceitas catalogoReceitas;
    private String ultimoResultado;

    public CozinhaPedido(CatalogoReceitas catalogoReceitas) {
        if (catalogoReceitas == null) {
            throw new IllegalArgumentException("ERR01 - O Catálogo de Receitas referenciado não pode ser nulo!");
        }
        this.catalogoReceitas = catalogoReceitas;
    }

    public void prepararLanche(Lanche lanche) {
        if (lanche == null) {
            throw new IllegalArgumentException("ERR01 - O Lanche referenciado não pode ser nulo!");
        }
        try {
            ReceitaLanche receita = this.catalogoReceitas.obterReceita(lanche.getDescricao());
            PreparoLanche preparo = SeletorPreparoLanche.obterPreparo(receita);
            preparo.prepararLanche();
            this.ultimoResultado = lanche.getDescricao() + " Em Preparo!";
        } catch (IllegalArgumentException e) {
            this.ultimoResultado = "A receita de " + lanche.getDescricao() + " não foi encontrada no catálogo!";
        }
    }

    public void descartarLanche(Lanche lanche) {
        if (lanche == null) {
            throw new IllegalArgumentException("ERR01 - O Lanche referenciado não pode ser nulo!");
        }
        this.ultimoResultado = lanche.getDescricao() + " Descartado!";
    }

    public String getUltimoResultado() {
        return ultimoResultado;
    }
}
