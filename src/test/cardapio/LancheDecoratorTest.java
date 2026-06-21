package test.cardapio;

import main.cardapio.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LancheDecoratorTest {

    @Test
    void deveRetornarPrecoECaloriasComAdicionalBacon() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalBacon(new Lanche("X-Teste", 20.0f, ingredientes));

        assertEquals(24.5f, lanche.getPreco());
        assertEquals(290, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComAdicionalCheddarExtra() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalCheddarExtra(new Lanche("X-Teste", 20.0f, ingredientes));

        assertEquals(23.0f, lanche.getPreco());
        assertEquals(260, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComAdicionalOvo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalOvo(new Lanche("X-Teste", 20.0f, ingredientes));

        assertEquals(22.5f, lanche.getPreco());
        assertEquals(270, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComBaconMaisCheddarExtra() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(new Lanche("X-Teste",
                20.0f, ingredientes)));

        assertEquals(27.5f, lanche.getPreco());
        assertEquals(350, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComBaconMaisOvo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalBacon(new AdicionalOvo(new Lanche("X-Teste", 20.0f,
                ingredientes)));

        assertEquals(27.0f, lanche.getPreco());
        assertEquals(360, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComCheddarExtraMaisOvo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalCheddarExtra(new AdicionalOvo(new Lanche("X-Teste", 20.0f,
                ingredientes)));

        assertEquals(25.5f, lanche.getPreco());
        assertEquals(330, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoECaloriasComBaconMaisCheddarExtraMaisOvo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(new AdicionalOvo(new Lanche("X-Teste",
                20.0f, ingredientes))));

        assertEquals(30.0f, lanche.getPreco());
        assertEquals(420, lanche.getCalorias());
    }

    @Test
    void deveRetornarDescricaoComAdicionalBacon() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalBacon(new Lanche("X-Teste", 20.0f, ingredientes));

        assertEquals("X-Teste + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComAdicionalCheddarExtra() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalCheddarExtra(new Lanche("X-Teste", 20.0f, ingredientes));

        assertEquals("X-Teste + Cheddar Extra", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComAdicionalOvo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalOvo(new Lanche("X-Teste", 20.0f, ingredientes));

        assertEquals("X-Teste + Ovo", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComBaconMaisCheddarExtra() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(new Lanche("X-Teste",
                20.0f, ingredientes)));

        assertEquals("X-Teste + Cheddar Extra + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComBaconMaisOvo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalBacon(new AdicionalOvo(new Lanche("X-Teste",
                20.0f, ingredientes)));

        assertEquals("X-Teste + Ovo + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComCheddarExtraMaisOvo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalCheddarExtra(new AdicionalOvo(new Lanche("X-Teste",
                20.0f, ingredientes)));

        assertEquals("X-Teste + Ovo + Cheddar Extra", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComBaconMaisCheddarExtraMaisOvo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(new AdicionalOvo(new Lanche("X-Teste",
                20.0f, ingredientes))));

        assertEquals("X-Teste + Ovo + Cheddar Extra + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarItemDecoradoOriginal() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        Lanche base = new Lanche("X-Teste", 20.0f, ingredientes);
        AdicionalBacon comBacon = new AdicionalBacon(base);

        assertSame(base, comBacon.getItemDecorado());
    }

    @Test
    void deveRetornarExcecaoParaItemNulo() {
        try {
            new AdicionalBacon(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("O item buscado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaItemQueNaoEhLanche() {
        try {
            new AdicionalBacon(new Bebida("Refrigerante", 6.0f, 140));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Somente lanches podem receber itens adicionais!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoAoTentarAlterarDescricaoDiretamente() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Decorator Teste", 200, false));
        ItemCardapio lanche = new AdicionalBacon(new Lanche("X-Teste", 20.0f, ingredientes));

        try {
            lanche.setDescricao("Outra Descrição");
            fail();
        } catch (UnsupportedOperationException e) {
            assertEquals("A descrição de um item decorado é derivada automaticamente, não podendo ser alterada!", e.getMessage());
        }
    }
}
