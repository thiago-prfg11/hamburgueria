package main.atendimento;

import main.cardapio.Ingrediente;
import main.cardapio.IngredienteFactory;

import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class InterpretadorPedidoTexto implements InterpretadorExpressaoPedido {

    private final InterpretadorExpressaoPedido interpretadorInicial;

    public InterpretadorPedidoTexto(String contexto) {
        if (contexto == null || contexto.isBlank()) {
            throw new IllegalArgumentException("ERR02 - A Expressão referenciada não pode ser nula ou em branco!");
        }

        String[] tokens = Pattern.compile(" \\+ | - ").split(contexto, -1);
        String[] operadores = extrairOperadores(contexto);

        Stack<InterpretadorExpressaoPedido> pilha = new Stack<>();
        pilha.push(resolverTermo(tokens[0].trim()));

        for (int i = 0; i < operadores.length; i++) {
            if (i + 1 >= tokens.length) {
                throw new IllegalArgumentException("ERR05 - A expressão não pode terminar com um operador!");
            }
            TermoIngredientes esquerda = (TermoIngredientes) pilha.pop();
            TermoIngredientes direita = resolverTermo(tokens[i + 1].trim());
            if (operadores[i].equals("+")) {
                pilha.push(new TermoIngredientes(new Adicionar(esquerda, direita).interpretar()));
            } else {
                pilha.push(new TermoIngredientes(new Remover(esquerda, direita).interpretar()));
            }
        }

        interpretadorInicial = pilha.pop();
    }

    private String[] extrairOperadores(String contexto) {
        java.util.List<String> ops = new java.util.ArrayList<>();
        java.util.regex.Matcher m = Pattern.compile(" (\\+|-) ").matcher(contexto);
        while (m.find()) {
            ops.add(m.group(1));
        }
        return ops.toArray(new String[0]);
    }

    private TermoIngredientes resolverTermo(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("ERR05 - A expressão não pode terminar com um operador!");
        }
        Ingrediente ingrediente = IngredienteFactory.buscarPorNome(nome);
        if (ingrediente == null) {
            throw new IllegalArgumentException("ERR01 - O ingrediente referenciado não pode ser nulo!");
        }
        return new TermoIngredientes(List.of(ingrediente));
    }

    public List<Ingrediente> interpretar() {
        return interpretadorInicial.interpretar();
    }
}