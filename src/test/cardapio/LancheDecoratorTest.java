package test.cardapio;

import main.cardapio.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LancheDecoratorTest {

    private Lanche criarLancheBase() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        return new Lanche("X-Teste", 20.0f, ingredientes);
    }

    @Test
    void deveRetornarPrecoECaloriasComAdicionalBacon() {
        ItemCardapio lanche = new AdicionalBacon(criarLancheBase());

        assertEquals(24.5f, lanche.getPreco());
        assertEquals(290, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComAdicionalCheddarExtra() {
        ItemCardapio lanche = new AdicionalCheddarExtra(criarLancheBase());

        assertEquals(23.0f, lanche.getPreco());
        assertEquals(260, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComAdicionalOvo() {
        ItemCardapio lanche = new AdicionalOvo(criarLancheBase());

        assertEquals(22.5f, lanche.getPreco());
        assertEquals(270, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComBaconMaisCheddarExtra() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(criarLancheBase()));

        assertEquals(27.5f, lanche.getPreco());
        assertEquals(350, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComBaconMaisOvo() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalOvo(criarLancheBase()));

        assertEquals(27.0f, lanche.getPreco());
        assertEquals(360, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComCheddarExtraMaisOvo() {
        ItemCardapio lanche = new AdicionalCheddarExtra(new AdicionalOvo(criarLancheBase()));

        assertEquals(25.5f, lanche.getPreco());
        assertEquals(330, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComBaconMaisCheddarExtraMaisOvo() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(new AdicionalOvo(criarLancheBase())));

        assertEquals(30.0f, lanche.getPreco());
        assertEquals(420, lanche.getCalorias());
    }

    @Test
    void deveRetornarDescricaoComAdicionalBacon() {
        ItemCardapio lanche = new AdicionalBacon(criarLancheBase());

        assertEquals("X-Teste + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComAdicionalCheddarExtra() {
        ItemCardapio lanche = new AdicionalCheddarExtra(criarLancheBase());

        assertEquals("X-Teste + Cheddar Extra", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComAdicionalOvo() {
        ItemCardapio lanche = new AdicionalOvo(criarLancheBase());

        assertEquals("X-Teste + Ovo", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComBaconMaisCheddarExtra() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(criarLancheBase()));

        assertEquals("X-Teste + Cheddar Extra + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComBaconMaisOvo() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalOvo(criarLancheBase()));

        assertEquals("X-Teste + Ovo + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComCheddarExtraMaisOvo() {
        ItemCardapio lanche = new AdicionalCheddarExtra(new AdicionalOvo(criarLancheBase()));

        assertEquals("X-Teste + Ovo + Cheddar Extra", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComBaconMaisCheddarExtraMaisOvo() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(new AdicionalOvo(criarLancheBase())));

        assertEquals("X-Teste + Ovo + Cheddar Extra + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarItemDecoradoOriginal() {
        Lanche base = criarLancheBase();
        AdicionalBacon comBacon = new AdicionalBacon(base);
        assertSame(base, comBacon.getItemDecorado());
    }

    @Test
    void deveRetornarExcecaoParaItemNulo() {
        try {
            new AdicionalBacon(null);
        } catch (IllegalArgumentException e) {
            assertEquals("O item buscado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaItemQueNaoELanche() {
        try {
            new AdicionalBacon(new Bebida("Refrigerante", 6.0f, 140));
        } catch (IllegalArgumentException e) {
            assertEquals("Somente lanches podem receber itens adicionais!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoAoTentarAlterarDescricaoDiretamente() {
        ItemCardapio lanche = new AdicionalBacon(criarLancheBase());
        try {
            lanche.setDescricao("Outra Descrição");
        } catch (UnsupportedOperationException e) {
            assertEquals("A descrição de um item decorado é derivada automaticamente, não podendo ser alterada!", e.getMessage());
        }
    }
}
