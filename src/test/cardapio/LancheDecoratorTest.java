package test.cardapio;

import main.cardapio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LancheDecoratorTest {

    Lanche lancheBase;

    @BeforeEach
    void setUp() {
        IngredienteFactory.limpar();
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 200, false));
        lancheBase = new Lanche("X-Tião", 20.0f, ingredientes);
    }

    @Test
    void deveRetornarPrecoComAdicionalBacon() {
        ItemCardapio lanche = new AdicionalBacon(lancheBase);
        assertEquals(24.5f, lanche.getPreco());
    }

    @Test
    void deveRetornarCaloriasComAdicionalBacon() {
        ItemCardapio lanche = new AdicionalBacon(lancheBase);
        assertEquals(290, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoComAdicionalCheddarExtra() {
        ItemCardapio lanche = new AdicionalCheddarExtra(lancheBase);
        assertEquals(23.0f, lanche.getPreco());
    }

    @Test
    void deveRetornarCaloriasComAdicionalCheddarExtra() {
        ItemCardapio lanche = new AdicionalCheddarExtra(lancheBase);
        assertEquals(260, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoComAdicionalOvo() {
        ItemCardapio lanche = new AdicionalOvo(lancheBase);
        assertEquals(22.5f, lanche.getPreco());
    }

    @Test
    void deveRetornarCaloriasComAdicionalOvo() {
        ItemCardapio lanche = new AdicionalOvo(lancheBase);
        assertEquals(270, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoComBaconMaisCheddarExtra() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(lancheBase));
        assertEquals(27.5f, lanche.getPreco());
    }

    @Test
    void deveRetornarCaloriasComBaconMaisCheddarExtra() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(lancheBase));
        assertEquals(350, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoComBaconMaisOvo() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalOvo(lancheBase));
        assertEquals(27.0f, lanche.getPreco());
    }

    @Test
    void deveRetornarCaloriasComBaconMaisOvo() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalOvo(lancheBase));
        assertEquals(360, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoComCheddarExtraMaisOvo() {
        ItemCardapio lanche = new AdicionalCheddarExtra(new AdicionalOvo(lancheBase));
        assertEquals(25.5f, lanche.getPreco());
    }

    @Test
    void deveRetornarCaloriasComCheddarExtraMaisOvo() {
        ItemCardapio lanche = new AdicionalCheddarExtra(new AdicionalOvo(lancheBase));
        assertEquals(330, lanche.getCalorias());
    }

    @Test
    void deveRetornarPrecoComBaconMaisCheddarExtraMaisOvo() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(new AdicionalOvo(lancheBase)));
        assertEquals(30.0f, lanche.getPreco());
    }

    @Test
    void deveRetornarCaloriasComBaconMaisCheddarExtraMaisOvo() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(new AdicionalOvo(lancheBase)));
        assertEquals(420, lanche.getCalorias());
    }

    @Test
    void deveRetornarDescricaoComAdicionalBacon() {
        ItemCardapio lanche = new AdicionalBacon(lancheBase);
        assertEquals("X-Tião + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComAdicionalCheddarExtra() {
        ItemCardapio lanche = new AdicionalCheddarExtra(lancheBase);
        assertEquals("X-Tião + Cheddar Extra", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComAdicionalOvo() {
        ItemCardapio lanche = new AdicionalOvo(lancheBase);
        assertEquals("X-Tião + Ovo", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComBaconMaisCheddarExtra() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(lancheBase));
        assertEquals("X-Tião + Cheddar Extra + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComBaconMaisOvo() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalOvo(lancheBase));
        assertEquals("X-Tião + Ovo + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComCheddarExtraMaisOvo() {
        ItemCardapio lanche = new AdicionalCheddarExtra(new AdicionalOvo(lancheBase));
        assertEquals("X-Tião + Ovo + Cheddar Extra", lanche.getDescricao());
    }

    @Test
    void deveRetornarDescricaoComBaconMaisCheddarExtraMaisOvo() {
        ItemCardapio lanche = new AdicionalBacon(new AdicionalCheddarExtra(new AdicionalOvo(lancheBase)));
        assertEquals("X-Tião + Ovo + Cheddar Extra + Bacon", lanche.getDescricao());
    }

    @Test
    void deveRetornarItemDecoradoOriginal() {
        AdicionalBacon comBacon = new AdicionalBacon(lancheBase);
        assertSame(lancheBase, comBacon.getItemDecorado());
    }

    @Test
    void deveRetornarExcecaoParaItemNulo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new AdicionalBacon(null));
        assertEquals("ERR01 - O item referenciado não pode ser nulo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaItemQueNaoEhLanche() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new AdicionalBacon(new Bebida("Refrigerante", 6.0f, 140)));
        assertEquals("ERR07 - Somente lanches podem receber itens adicionais!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoAoTentarAlterarDescricaoDiretamente() {
        ItemCardapio lanche = new AdicionalBacon(lancheBase);
        UnsupportedOperationException e = assertThrows(UnsupportedOperationException.class,
                () -> lanche.setDescricao("Outra Descrição"));
        assertEquals("ERR07 - A descrição de um item decorado é derivada automaticamente," +
                " não podendo ser alterada!", e.getMessage());
    }
}
