package main.cozinha;

import main.cardapio.Ingrediente;
import main.cardapio.Lanche;
import main.cardapio.ReceitaLanche;

import java.util.ArrayList;
import java.util.List;

public abstract class PreparoLanche {

    private final ReceitaLanche receita;
    protected List<String> etapas;

    public PreparoLanche(ReceitaLanche receita) {
        if (receita == null) {
            throw new IllegalArgumentException("ERR01 - A receita referenciada não pode ser nula!");
        }
        if (receita.getIngredientesBase().size() < 2) {
            throw new IllegalArgumentException("ERR07 - A receita referenciada deve conter ao menos um pão" +
                    " e uma proteína!");
        }
        this.receita = receita;
    }

    public String prepararLanche() {
        this.etapas = new ArrayList<>();
        List<Ingrediente> ingredientes = this.receita.getIngredientesBase();
        tostarEIncluirPao(ingredientes.get(0));
        prepararProteina(ingredientes.get(1));
        adicionarCamadas(ingredientes.size() > 2 ? ingredientes.subList(2, ingredientes.size()) : new ArrayList<>());
        embalar();
        return String.join(" + ", this.etapas);
    }

    protected void tostarEIncluirPao(Ingrediente pao) {
        etapas.add("Tostar e Adicionar " + pao.nome());
    }

    protected abstract void prepararProteina(Ingrediente proteina);

    protected void adicionarCamadas(List<Ingrediente> camadas) {
        if (camadas.isEmpty()) {
            etapas.add("Sem Camadas Adicionais");
            return;
        }
        List<String> nomes = new ArrayList<>();
        for (Ingrediente ingrediente : camadas) {
            nomes.add(ingrediente.nome());
        }
        etapas.add("Adicionar " + String.join(", ", nomes));
    }

    protected void embalar() {
        etapas.add("Embalar em Papel Manteiga");
    }

    public Lanche finalizar() {
        return new Lanche(this.receita);
    }
}
