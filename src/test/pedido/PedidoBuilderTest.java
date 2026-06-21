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
    void deveArmazenarDadosBasicosDoPedido() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-BUILDER-001")
                .setNomeCliente("Carlos Silva")
                .setEnderecoEntrega("Rua das Flores, 123")
                .setFormaPagamento("Cartão")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(new Bebida("Refrigerante", 6.0f, 140))
                .build();

        assertEquals("PED-BUILDER-001", pedido.getCodigoPedido());
        assertEquals("Carlos Silva", pedido.getNomeCliente());
        assertEquals("Rua das Flores, 123", pedido.getEnderecoEntrega());
        assertEquals("Cartão", pedido.getFormaPagamento());
        assertEquals("Recebido", pedido.getDescricaoEstado());
    }

    @Test
    void deveCalcularValorTotalSomandoItensEFrete() {
        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-BUILDER-002")
                .setNomeCliente("Beatriz Lima")
                .setEstrategiaFrete(new FretePorKm(2.0f))
                .setDistanciaKm(5.0f)
                .addItem(new Bebida("Refrigerante", 6.0f, 140))
                .addItem(new Acompanhamento("Batata", 10.0f, 400))
                .build();

        assertEquals(10.0f, pedido.getValorFrete(), 0.01f);
        assertEquals(26.0f, pedido.getValorTotal(), 0.01f);
    }

    @Test
    void deveArmazenarItemDecoradoClonadoDoPrototypeComPrecoCorreto() {
        CatalogoReceitas catalogo = new CatalogoReceitas();
        List<Ingrediente> ingredientesBase = new ArrayList<>();
        ingredientesBase.add(IngredienteFactory.getIngrediente("Pão Builder Teste", 150, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Builder", ingredientesBase, 22.0f,
                15, TecnicaPreparo.TRADICIONAL));

        Lanche lancheBase = new Lanche(catalogo.obterReceita("X-Builder"));
        ItemCardapio lancheComBacon = new AdicionalBacon(lancheBase);

        Pedido pedido = new PedidoBuilder()
                .setCodigoPedido("PED-BUILDER-003")
                .setNomeCliente("Carlos Silva")
                .setEstrategiaFrete(new FreteFixo())
                .addItem(lancheComBacon)
                .build();

        assertEquals(26.5f, pedido.getItens().get(0).getPreco(), 0.01f);
    }

    @Test
    void deveRemoverItemAntesDoBuild() {
        Bebida refrigerante = new Bebida("Refrigerante", 6.0f, 140);
        PedidoBuilder builder = new PedidoBuilder()
                .addItem(refrigerante)
                .addItem(new Acompanhamento("Batata", 10.0f, 400));

        builder.removerItem(refrigerante);

        assertEquals(1, builder.getItensAtuais().size());
    }

    @Test
    void deveRetornarExcecaoParaBuildSemCodigoPedido() {
        try {
            new PedidoBuilder()
                    .setNomeCliente("Cliente Teste")
                    .setEstrategiaFrete(new FreteFixo())
                    .addItem(new Bebida("Água", 4.0f, 0))
                    .build();
        } catch (IllegalArgumentException e) {
            assertEquals("O código do pedido não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaBuildSemNomeCliente() {
        try {
            new PedidoBuilder()
                    .setCodigoPedido("PED-003")
                    .setEstrategiaFrete(new FreteFixo())
                    .addItem(new Bebida("Água", 4.0f, 0))
                    .build();
        } catch (IllegalArgumentException e) {
            assertEquals("O nome do cliente não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaBuildSemItens() {
        try {
            new PedidoBuilder()
                    .setCodigoPedido("PED-004")
                    .setNomeCliente("Cliente Teste")
                    .setEstrategiaFrete(new FreteFixo())
                    .build();
        } catch (IllegalArgumentException e) {
            assertEquals("O pedido deve ter ao menos um item!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaBuildSemEstrategiaFrete() {
        try {
            new PedidoBuilder()
                    .setCodigoPedido("PED-005")
                    .setNomeCliente("Cliente Teste")
                    .addItem(new Bebida("Água", 4.0f, 0))
                    .build();
        } catch (IllegalArgumentException e) {
            assertEquals("A estratégia de frete não pode ser nula!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaCodigoPedidoNuloNoSetter() {
        try {
            new PedidoBuilder().setCodigoPedido(null);
        } catch (IllegalArgumentException e) {
            assertEquals("O código do pedido não pode ser nulo ou vazio!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaNomeClienteVazioNoSetter() {
        try {
            new PedidoBuilder().setNomeCliente("   ");
        } catch (IllegalArgumentException e) {
            assertEquals("O nome do cliente não pode ser nulo ou vazio!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaItemNuloNoAddItem() {
        try {
            new PedidoBuilder().addItem(null);
        } catch (IllegalArgumentException e) {
            assertEquals("O item inserido não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaDistanciaNegativaNoSetter() {
        try {
            new PedidoBuilder().setDistanciaKm(-3.0f);
        } catch (IllegalArgumentException e) {
            assertEquals("A distância para entrega não pode ser negativa!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaEstrategiaFreteNulaNoSetter() {
        try {
            new PedidoBuilder().setEstrategiaFrete(null);
        } catch (IllegalArgumentException e) {
            assertEquals("A estratégia de frete não pode ser nula!", e.getMessage());
        }
    }

    // Testes Referentes a Comunicação Com o Padrão Memento

    @Test
    void deveArmazenarSnapshotsAposAlteracoesNoCarrinho() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Refrigerante", 6.0f, 140));
        builder.addItem(new Acompanhamento("Batata", 10.0f, 400));
        assertEquals(2, builder.getHistorico().size());
    }

    @Test
    void deveRestaurarParaPrimeiroSnapshot() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Refrigerante", 6.0f, 140));
        builder.addItem(new Acompanhamento("Batata", 10.0f, 400));
        builder.restaurarParaIndice(0);
        assertEquals(1, builder.getItensAtuais().size());
        assertEquals("Refrigerante", builder.getItensAtuais().get(0).getDescricao());
    }

    @Test
    void deveRestaurarParaSnapshotIntermediario() {
        PedidoBuilder builder = new PedidoBuilder();
        Bebida refrigerante = new Bebida("Refrigerante", 6.0f, 140);
        Acompanhamento batata = new Acompanhamento("Batata", 10.0f, 400);
        builder.addItem(refrigerante);
        builder.addItem(batata);
        builder.removerItem(refrigerante);
        builder.addItem(new Acompanhamento("Onion Rings", 13.9f, 350));
        builder.restaurarParaIndice(2);
        assertEquals(1, builder.getItensAtuais().size());
        assertEquals("Batata", builder.getItensAtuais().get(0).getDescricao());
    }

    @Test
    void naoDeveAlterarSnapshotAntigoAposNovasModificacoesNoCarrinho() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Refrigerante", 6.0f, 140));
        List<ItemCardapio> primeiroSnapshot = builder.getHistorico().get(0).getItens();
        builder.addItem(new Acompanhamento("Batata", 10.0f, 400));
        builder.addItem(new Acompanhamento("Onion Rings", 13.9f, 350));
        assertEquals(1, primeiroSnapshot.size());
    }

    @Test
    void deveRetornarExcecaoParaIndiceInvalido() {
        PedidoBuilder builder = new PedidoBuilder();
        try {
            builder.restaurarParaIndice(0);
        } catch (IllegalArgumentException e) {
            assertEquals("O índice inserido para restauração não é válido!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaIndiceNegativo() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Suco", 7.0f, 90));
        try {
            builder.restaurarParaIndice(-1);
        } catch (IllegalArgumentException e) {
            assertEquals("O índice inserido para restauração não é válido!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaIndiceAcimaDoLimite() {
        PedidoBuilder builder = new PedidoBuilder();
        builder.addItem(new Bebida("Suco", 7.0f, 90));
        try {
            builder.restaurarParaIndice(5);
        } catch (IllegalArgumentException e) {
            assertEquals("O índice inserido para restauração não é válido!", e.getMessage());
        }
    }

}
