package test.cozinha;

import main.cardapio.*;
import main.cozinha.CozinhaPedido;
import main.cozinha.InicioPreparoTarefa;
import main.cozinha.PainelCozinha;
import main.pedido.FreteFixo;
import main.pedido.Pedido;
import main.pedido.PedidoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PainelCozinhaTest {

    CatalogoReceitas catalogo;
    CozinhaPedido cozinhaPedido;
    PainelCozinha painel;

    @BeforeEach
    void setUp() {
        catalogo = new CatalogoReceitas();

        List<Ingrediente> ingredientesXBurger = new ArrayList<>();
        ingredientesXBurger.add(IngredienteFactory.getIngrediente("Pão Brioche Painel", 120, false));
        ingredientesXBurger.add(IngredienteFactory.getIngrediente("Carne Angus Painel", 280, false));
        ingredientesXBurger.add(IngredienteFactory.getIngrediente("Queijo Cheddar Painel", 90, true));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Burger Painel", ingredientesXBurger, 22.90f, 12, TecnicaPreparo.TRADICIONAL));

        List<Ingrediente> ingredientesFrango = new ArrayList<>();
        ingredientesFrango.add(IngredienteFactory.getIngrediente("Pão Australiano Painel", 110, false));
        ingredientesFrango.add(IngredienteFactory.getIngrediente("Filé de Frango Painel", 220, false));
        catalogo.cadastrarReceita(new ReceitaLanche("Frango Crispy Painel", ingredientesFrango, 21.90f, 14, TecnicaPreparo.CHICKEN));

        cozinhaPedido = new CozinhaPedido(catalogo);
        painel = new PainelCozinha(cozinhaPedido);
    }

    @Test
    void deveAdicionarLanchesNaFilaAoReceberPedido() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-001")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger Painel")))
                .addItem(new Bebida("Refrigerante Painel", 6.0f, 140))
                .build();
        pedido.confirmarPreparo();

        painel.receberPedido(pedido);

        assertEquals(1, painel.getFilaEspera().size());
    }

    @Test
    void naoDeveAdicionarBebidaNaFila() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-002")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Suco Painel", 7.0f, 90))
                .build();
        pedido.confirmarPreparo();

        painel.receberPedido(pedido);

        assertEquals(0, painel.getFilaEspera().size());
    }

    @Test
    void deveExecutarProximaTarefaEMoverParaHistorico() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-003")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger Painel")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);

        painel.executarProximaTarefa();

        assertEquals(0, painel.getFilaEspera().size());
        assertEquals(1, painel.getHistorico().size());
    }

    @Test
    void deveRegistrarInicioDePreparoAoExecutarTarefa() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-004")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger Painel")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);

        painel.executarProximaTarefa();

        assertEquals("X-Burger Painel Em Preparo!", cozinhaPedido.getUltimoResultado());
    }

    @Test
    void deveRegistrarInicioDePreparoParaLancheChicken() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-005")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("Frango Crispy Painel")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);

        painel.executarProximaTarefa();

        assertEquals("Frango Crispy Painel Em Preparo!", cozinhaPedido.getUltimoResultado());
    }

    @Test
    void deveDesfazerUltimaTarefaEDevolverParaFila() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-006")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger Painel")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);
        painel.executarProximaTarefa();

        painel.desfazerUltimaTarefa();

        assertEquals(1, painel.getFilaEspera().size());
        assertEquals(0, painel.getHistorico().size());
    }

    @Test
    void deveRegistrarDescarteAoDesfazerTarefa() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-007")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger Painel")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);
        painel.executarProximaTarefa();

        painel.desfazerUltimaTarefa();

        assertEquals("X-Burger Painel Descartado!", cozinhaPedido.getUltimoResultado());
    }

    @Test
    void deveManterOrdemCorretaNaFilaAoDesfazer() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-008")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger Painel")))
                .addItem(new Lanche(catalogo.obterReceita("Frango Crispy Painel")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);
        painel.executarProximaTarefa();
        painel.desfazerUltimaTarefa();

        painel.executarProximaTarefa();

        assertEquals("X-Burger Painel Em Preparo!", cozinhaPedido.getUltimoResultado());
    }

    @Test
    void deveRegistrarErroQuandoReceitaNaoEncontradaNoCatalogo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Sem Receita Painel", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Carne Sem Receita Painel", 280, false));
        Lanche lancheSemReceita = new Lanche("Lanche Sem Receita", 15.0f, ingredientes);

        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-009")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(lancheSemReceita)
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);

        painel.executarProximaTarefa();

        assertEquals("A receita de Lanche Sem Receita não foi encontrada no catálogo!", cozinhaPedido.getUltimoResultado());
    }

    @Test
    void naoDeveFalharAoExecutarTarefaSemFilaDeEspera() {
        painel.executarProximaTarefa();

        assertEquals(0, painel.getHistorico().size());
    }

    @Test
    void naoDeveFalharAoDesfazerSemHistorico() {
        painel.desfazerUltimaTarefa();

        assertEquals(0, painel.getFilaEspera().size());
    }

    @Test
    void deveRetornarFilaDeEsperaIndependenteDaOriginal() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-010")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger Painel")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);

        painel.getFilaEspera().clear();

        assertEquals(1, painel.getFilaEspera().size());
    }

    @Test
    void deveRetornarExcecaoParaPedidoNulo() {
        try {
            painel.receberPedido(null);
        } catch (IllegalArgumentException e) {
            assertEquals("O pedido referenciado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaPedidoForaDoEstadoEmPreparo() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-CMD-011")
                .setNomeCliente("Cliente Command")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger Painel")))
                .build();

        try {
            painel.receberPedido(pedido);
        } catch (IllegalStateException e) {
            assertEquals("O pedido deve estar com o status 'Em Preparo' para ser enviado à cozinha!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCozinhaNulaNoPainelCozinha() {
        try {
            new PainelCozinha(null);
        } catch (IllegalArgumentException e) {
            assertEquals("A cozinha referenciada não pode ser nula!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCatalogoNuloNoCozinhaPedido() {
        try {
            new CozinhaPedido(null);
        } catch (IllegalArgumentException e) {
            assertEquals("O Catálogo de Receitas referenciado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaLancheNuloNoInicioPreparoTarefa() {
        try {
            new InicioPreparoTarefa(cozinhaPedido, null);
        } catch (IllegalArgumentException e) {
            assertEquals("O lanche referenciado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCozinhaNulaNoInicioPreparoTarefa() {
        try {
            new InicioPreparoTarefa(null, new Lanche(catalogo.obterReceita("X-Burger Painel")));
        } catch (IllegalArgumentException e) {
            assertEquals("A cozinha referenciada não pode ser nula!", e.getMessage());
        }
    }
}