package main.atendimento;

import main.cardapio.Ingrediente;
import main.cardapio.IngredienteFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class InterpretadorPedidoTexto implements InterpretadorExpressaoPedido {

    private final InterpretadorExpressaoPedido interpretadorInicial;

    public InterpretadorPedidoTexto(String contexto) {
        if (contexto == null || contexto.isBlank()) {
            throw new IllegalArgumentException("Expressão inválida");
        }

        Stack<InterpretadorExpressaoPedido> pilhaInterpretadores = new Stack<>();
        List<String> elementos = Arrays.asList(contexto.split(" "));
        Iterator<String> iterator = elementos.iterator();

        while (iterator.hasNext()) {
            String elemento = iterator.next();
            if (elemento.equals("+")) {
                if (!iterator.hasNext())
                    throw new IllegalArgumentException("Expressão inválida");
                TermoIngredientes elementoEsquerda = (TermoIngredientes) pilhaInterpretadores.pop();
                TermoIngredientes elementoDireita = resolverTermo(iterator.next());
                Adicionar interpretador = new Adicionar(elementoEsquerda, elementoDireita);
                pilhaInterpretadores.push(new TermoIngredientes(interpretador.interpretar()));
            } else if (elemento.equals("-")) {
                if (!iterator.hasNext())
                    throw new IllegalArgumentException("Expressão inválida");
                TermoIngredientes elementoEsquerda = (TermoIngredientes) pilhaInterpretadores.pop();
                TermoIngredientes elementoDireita = resolverTermo(iterator.next());
                Remover interpretador = new Remover(elementoEsquerda, elementoDireita);
                pilhaInterpretadores.push(new TermoIngredientes(interpretador.interpretar()));
            } else {
                pilhaInterpretadores.push(resolverTermo(elemento));
            }
        }
        interpretadorInicial = pilhaInterpretadores.pop();
    }

    private TermoIngredientes resolverTermo(String nome) {
        Ingrediente ingrediente = IngredienteFactory.buscarPorNome(nome);
        if (ingrediente == null) {
            throw new IllegalArgumentException("Expressão com elemento inválido");
        }
        return new TermoIngredientes(List.of(ingrediente));
    }

    public List<Ingrediente> interpretar() {
        return interpretadorInicial.interpretar();
    }
}
