package main.atendimento;

import main.cardapio.Ingrediente;

import java.util.ArrayList;
import java.util.List;

public class TermoIngredientes implements InterpretadorExpressaoPedido {

    private final List<Ingrediente> ingredientes;

    public TermoIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = new ArrayList<>(ingredientes);
    }

    public List<Ingrediente> interpretar() {
        return new ArrayList<>(this.ingredientes);
    }
}
