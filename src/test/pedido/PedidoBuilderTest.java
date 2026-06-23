package test.pedido;

import main.cardapio.*;
import main.pedido.FreteFixo;
import main.pedido.FretePorKm;
import main.pedido.Pedido;
import main.pedido.PedidoBuilder;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PedidoBuilderTest {

    // Testes Referentes Exclusivamente ao Builder

    @Test
    void deveArmazenarCodigoDoPedido() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-001")
                .setNomeCliente("Carlos Silva")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Pepsi – Lata 350 ml", 6.0f, 140))
                .build();
        assertEquals("PED-001", pedido.getCodigoPedido());
    }

    @Test
    void deveArmazenarNomeDoCliente() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-001")
                .setNomeCliente("Carlos Silva")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Pepsi – Lata 350 ml", 6.0f, 140))
                .build();
        assertEquals("Carlos Silva", pedido.getNomeCliente());
    }

    @Test
    void deveArmazenarEnderecoDeEntrega() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-001")
                .setNomeCliente("Carlos Silva")
                .setEnderecoEntrega("Rua das Flores, 123")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Pepsi – Lata 350 ml", 6.0f, 140))
                .build();
        assertEquals("Rua das Flores, 123", pedido.getEnderecoEntrega());
    }

    @Test
    void deveArmazenarFormaDePagamento() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-001")
                .setNomeCliente("Carlos Silva")
                .setFormaPagamento("Cartão")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Pepsi – Lata 350 ml", 6.0f, 140))
                .build();
        assertEquals("Cartão", pedido.getFormaPagamento());
    }

    @Test
    void deveIniciarPedidoNoEstadoRecebido() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-001")
                .setNomeCliente("Carlos Silva")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Pepsi – Lata 350 ml", 6.0f, 140))
                .build();
        assertEquals("Recebido", pedido.getDescricaoEstado());
    }

    @Test
    void deveCalcularValorDoFreteCorretamente() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-002")
                .setNomeCliente("Beatriz Lima")
                .setEstrategiaFrete(new FretePorKm(2.0f))
                .setDistanciaKm(5.0f)
                .addItem(new Bebida("Guaraná Antarctica – Lata 350 ml", 6.0f, 140))
                .addItem(new Acompanhamento("Batata Média", 10.0f, 400))
                .build();
        assertEquals(10.0f, pedido.getValorFrete(), 0.01f);
    }

    @Test
    void deveCalcularValorTotalSomandoItensEFrete() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-002")
                .setNomeCliente("Beatriz Lima")
                .setEstrategiaFrete(new FretePorKm(2.0f))
                .setDistanciaKm(5.0f)
                .addItem(new Bebida("Guaraná Antarctica – Lata 350 ml", 6.0f, 140))
                .addItem(new Acompanhamento("Batata Média", 10.0f, 400))
                .build();
        assertEquals(26.0f, pedido.getValorTotal(), 0.01f);
    }

    @Test
    void deveArmazenarItemDecoradoClonadoDoPrototypeComPrecoCorreto() {
        CatalogoReceitas catalogo = new CatalogoReceitas();
        List<Ingrediente> ingredientesBase = new ArrayList<>();
        ingredientesBase.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 150, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Bread", ingredientesBase, 22.0f,
                15, TecnicaPreparo.TRADICIONAL));

        Lanche lancheBase = new Lanche(catalogo.obterReceita("X-Bread"));
        ItemCardapio lancheComBacon = new AdicionalBacon(lancheBase);

        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-003")
                .setNomeCliente("Carlos Silva")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(lancheComBacon)
                .build();

        assertEquals(26.5f, pedido.getItens().getFirst().getPreco(), 0.01f);
    }

    @Test
    void deveRemoverItemAntesDoBuild() {
        Bebida refrigerante = new Bebida("Natural One – Suco de Laranja Integral", 6.0f, 140);
        PedidoBuilder builder = new PedidoBuilder()
                .addItem(refrigerante)
                .addItem(new Acompanhamento("Batata Pequena", 10.0f, 400));

        builder.removerItem(refrigerante);

        assertEquals(1, builder.getItensAtuais().size());
    }

    @Test
    void deveRetornarExcecaoParaBuildSemCodigoPedido() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PedidoBuilder()
                        .setNomeCliente("Marilda")
                        .setEstrategiaFrete(new FreteFixo())
                        .addItem(new Bebida("Água Sem Gás", 4.0f, 0))
                        .build());
        assertEquals("ERR01 - O código do pedido não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaBuildSemNomeCliente() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PedidoBuilder()
                        .setCodigoPedido("PED-003")
                        .setEstrategiaFrete(new FreteFixo())
                        .addItem(new Bebida("Água Com Gás", 4.0f, 0))
                        .build());
        assertEquals("ERR01 - O nome do cliente não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaBuildSemItens() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PedidoBuilder()
                        .setCodigoPedido("PED-004")
                        .setNomeCliente("Arthur Silva")
                        .setEstrategiaFrete(new FreteFixo())
                        .build());
        assertEquals("ERR02 - O pedido deve ter ao menos um item!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaBuildSemEstrategiaFrete() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PedidoBuilder()
                        .setCodigoPedido("PED-005")
                        .setNomeCliente("Luiz Carlos Mendonça")
                        .addItem(new Bebida("Maguary – Suco de Laranja", 4.0f, 0))
                        .build());
        assertEquals("ERR01 - A estratégia de frete referenciada não pode ser nula!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaCodigoPedidoNuloNoSetter() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PedidoBuilder().setCodigoPedido(null));
        assertEquals("ERR02 - O código do pedido não pode ser nulo ou vazio!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaNomeClienteVazioNoSetter() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PedidoBuilder().setNomeCliente("   "));
        assertEquals("ERR02 - O nome do cliente não pode ser nulo ou vazio!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaItemNuloNoAddItem() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PedidoBuilder().addItem(null));
        assertEquals("ERR01 - O item referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaDistanciaNegativaNoSetter() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PedidoBuilder().setDistanciaKm(-3.0f));
        assertEquals("ERR03 - A distância para entrega não pode ser negativa!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaEstrategiaFreteNulaNoSetter() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PedidoBuilder().setEstrategiaFrete(null));
        assertEquals("ERR01 - A estratégia de frete referenciada não pode ser nula!", e.getMessage());
    }

    // Testes Referentes a Comunicação Com o Padrão Memento

    @Test
    void deveArmazenarSnapshotsAposAlteracoesNoCarrinho() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Coca-Cola – Lata 350 ml", 6.0f, 140));
        builder.addItem(new Acompanhamento("Batata Grande", 10.0f, 400));
        assertEquals(2, builder.getHistorico().size());
    }

    @Test
    void deveRestaurarParaItensDoIndiceZero() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Guaraná Antarctica – Lata 350 ml", 6.0f, 140));
        builder.addItem(new Acompanhamento("Batata Pequena", 10.0f, 400));
        builder.restaurarParaIndice(0);
        assertEquals(1, builder.getItensAtuais().size());
    }

    @Test
    void deveRestaurarParaItemCorretoNoIndiceZero() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Guaraná Antarctica – Lata 350 ml", 6.0f, 140));
        builder.addItem(new Acompanhamento("Batata Pequena", 10.0f, 400));
        builder.restaurarParaIndice(0);
        assertEquals("Guaraná Antarctica – Lata 350 ml", builder.getItensAtuais().getFirst().getDescricao());
    }

    @Test
    void deveRestaurarParaQuantidadeCorretaNoSnapshotIntermediario() {
        PedidoBuilder builder = new PedidoBuilder();
        Bebida refrigerante = new Bebida("Pepsi – Lata 350 ml", 6.0f, 140);
        Acompanhamento batata = new Acompanhamento("Batata Média", 10.0f, 400);
        builder.addItem(refrigerante);
        builder.addItem(batata);
        builder.removerItem(refrigerante);
        builder.addItem(new Acompanhamento("Onion Rings", 13.9f, 350));
        builder.restaurarParaIndice(2);
        assertEquals(1, builder.getItensAtuais().size());
    }

    @Test
    void deveRestaurarParaItemCorretoNoSnapshotIntermediario() {
        PedidoBuilder builder = new PedidoBuilder();
        Bebida refrigerante = new Bebida("Pepsi – Lata 350 ml", 6.0f, 140);
        Acompanhamento batata = new Acompanhamento("Batata Média", 10.0f, 400);
        builder.addItem(refrigerante);
        builder.addItem(batata);
        builder.removerItem(refrigerante);
        builder.addItem(new Acompanhamento("Onion Rings", 13.9f, 350));
        builder.restaurarParaIndice(2);
        assertEquals("Batata Média", builder.getItensAtuais().getFirst().getDescricao());
    }

    @Test
    void naoDeveAlterarSnapshotAntigoAposNovasModificacoesNoCarrinho() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Pepsi – Lata 350 ml", 6.0f, 140));
        List<ItemCardapio> primeiroSnapshot = builder.getHistorico().getFirst().itens();
        builder.addItem(new Acompanhamento("Batata Pequena", 10.0f, 400));
        builder.addItem(new Acompanhamento("Onion Rings", 13.9f, 350));
        assertEquals(1, primeiroSnapshot.size());
    }

    @Test
    void deveRetornarExcecaoParaIndiceInvalido() {
        PedidoBuilder builder = new PedidoBuilder();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> builder.restaurarParaIndice(0));
        assertEquals("ERR07 - O índice inserido para restauração não é válido!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaIndiceNegativo() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Maguary – Suco de Laranja", 7.0f, 90));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> builder.restaurarParaIndice(-1));
        assertEquals("ERR07 - O índice inserido para restauração não é válido!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaIndiceAcimaDoLimite() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Maguary – Suco de Laranja", 7.0f, 90));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> builder.restaurarParaIndice(5));
        assertEquals("ERR07 - O índice inserido para restauração não é válido!", e.getMessage());
    }
}
