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
        IngredienteFactory.limpar();
        catalogo = new CatalogoReceitas();

        List<Ingrediente> ingredientesXBurger = new ArrayList<>();
        ingredientesXBurger.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ingredientesXBurger.add(IngredienteFactory.getIngrediente("Hambúrguer Angus 180g", 280, false));
        ingredientesXBurger.add(IngredienteFactory.getIngrediente("President – Queijo Cheddar", 90, true));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Burger", ingredientesXBurger, 22.90f,
                12, TecnicaPreparo.TRADICIONAL));

        List<Ingrediente> ingredientesFrango = new ArrayList<>();
        ingredientesFrango.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 110, false));
        ingredientesFrango.add(IngredienteFactory.getIngrediente("Sadia – Filé de Peito Empanado Crocante", 220, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Crispy", ingredientesFrango, 21.90f,
                14, TecnicaPreparo.CHICKEN));

        cozinhaPedido = new CozinhaPedido(catalogo);
        painel = new PainelCozinha(cozinhaPedido);
    }

    @Test
    void deveAdicionarLanchesNaFilaAoReceberPedido() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-001")
                .setNomeCliente("Marco")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger")))
                .addItem(new Bebida("Coca-Cola – Lata 350 ml", 6.0f, 140))
                .build();
        pedido.confirmarPreparo();

        painel.receberPedido(pedido);

        assertEquals(1, painel.getFilaEspera().size());
    }

    @Test
    void naoDeveAdicionarBebidaNaFila() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-002")
                .setNomeCliente("Elisa")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Natural One – Suco de Laranja Integral", 7.0f, 90))
                .build();
        pedido.confirmarPreparo();

        painel.receberPedido(pedido);

        assertEquals(0, painel.getFilaEspera().size());
    }

    @Test
    void deveExecutarProximaTarefaEMoverParaHistorico() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-003")
                .setNomeCliente("Daniel")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger")))
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
                .setCodigoPedido("PED-004")
                .setNomeCliente("Douglas")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);

        painel.executarProximaTarefa();

        assertEquals("X-Burger Em Preparo!", cozinhaPedido.getUltimoResultado());
    }

    @Test
    void deveRegistrarInicioDePreparoParaLancheChicken() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-005")
                .setNomeCliente("Thallison")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Crispy")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);

        painel.executarProximaTarefa();

        assertEquals("X-Crispy Em Preparo!", cozinhaPedido.getUltimoResultado());
    }

    @Test
    void deveDesfazerUltimaTarefaEDevolverParaFila() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-006")
                .setNomeCliente("Samara")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger")))
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
                .setCodigoPedido("PED-007")
                .setNomeCliente("Débora")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);
        painel.executarProximaTarefa();

        painel.desfazerUltimaTarefa();

        assertEquals("X-Burger Descartado!", cozinhaPedido.getUltimoResultado());
    }

    @Test
    void deveManterOrdemCorretaNaFilaAoDesfazer() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-008")
                .setNomeCliente("Davi")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger")))
                .addItem(new Lanche(catalogo.obterReceita("X-Crispy")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);
        painel.executarProximaTarefa();
        painel.desfazerUltimaTarefa();

        painel.executarProximaTarefa();

        assertEquals("X-Burger Em Preparo!", cozinhaPedido.getUltimoResultado());
    }

    @Test
    void deveRegistrarErroQuandoReceitaNaoEncontradaNoCatalogo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false));
        Lanche lancheSemReceita = new Lanche("X-Maximo", 15.0f, ingredientes);

        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-009")
                .setNomeCliente("Matheus")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(lancheSemReceita)
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);

        painel.executarProximaTarefa();

        assertEquals("A receita de X-Maximo não foi encontrada no catálogo!", cozinhaPedido.getUltimoResultado());
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
                .setCodigoPedido("PED-010")
                .setNomeCliente("Alice")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger")))
                .build();
        pedido.confirmarPreparo();
        painel.receberPedido(pedido);

        painel.getFilaEspera().clear();

        assertEquals(1, painel.getFilaEspera().size());
    }

    @Test
    void deveRetornarExcecaoParaPedidoNulo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> painel.receberPedido(null));
        assertEquals("ERR01 - O pedido referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaPedidoForaDoEstadoEmPreparo() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-011")
                .setNomeCliente("César")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Lanche(catalogo.obterReceita("X-Burger")))
                .build();

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> painel.receberPedido(pedido));
        assertEquals("ERR07 - O pedido deve estar com o status 'Em Preparo' para ser enviado à cozinha!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCozinhaNulaNoPainelCozinha() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PainelCozinha(null));
        assertEquals("ERR01 - A cozinha referenciada não pode ser nula!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCatalogoNuloNoCozinhaPedido() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new CozinhaPedido(null));
        assertEquals("ERR01 - O Catálogo de Receitas referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaLancheNuloNoInicioPreparoTarefa() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new InicioPreparoTarefa(cozinhaPedido, null));
        assertEquals("ERR01 - O lanche referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCozinhaNulaNoInicioPreparoTarefa() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false));
        Lanche lanche = new Lanche("X-Burger", 22.90f, ingredientes);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new InicioPreparoTarefa(null, lanche));
        assertEquals("ERR01 - A cozinha referenciada não pode ser nula!", e.getMessage());
    }
}