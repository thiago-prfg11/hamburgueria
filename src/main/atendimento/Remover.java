package main.atendimento;

import main.cardapio.Ingrediente;

import java.util.ArrayList;
import java.util.List;

public class Remover implements InterpretadorExpressaoPedido {

    private final List<Ingrediente> esquerda;
    private final List<Ingrediente> direita;

    public Remover(TermoIngredientes elementoEsquerda, TermoIngredientes elementoDireita) {
        esquerda = elementoEsquerda.interpretar();
        direita = elementoDireita.interpretar();
    }

    public List<Ingrediente> interpretar() {
        List<Ingrediente> resultado = new ArrayList<>(esquerda);
        resultado.removeAll(direita);
        return resultado;
    }
}
