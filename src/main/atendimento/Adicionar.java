package main.atendimento;

import main.cardapio.Ingrediente;
import java.util.ArrayList;
import java.util.List;

public class Adicionar implements InterpretadorExpressaoPedido {

    private final List<Ingrediente> esquerda;
    private final List<Ingrediente> direita;

    public Adicionar(TermoIngredientes elementoEsquerda, TermoIngredientes elementoDireita) {
        esquerda = elementoEsquerda.interpretar();
        direita = elementoDireita.interpretar();
    }

    public List<Ingrediente> interpretar() {
        List<Ingrediente> resultado = new ArrayList<>(esquerda);
        resultado.addAll(direita);
        return resultado;
    }
}
