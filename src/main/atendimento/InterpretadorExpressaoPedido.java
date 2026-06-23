package main.atendimento;

import main.cardapio.Ingrediente;
import java.util.List;

public interface InterpretadorExpressaoPedido {

    List<Ingrediente> interpretar();
}
