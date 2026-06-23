package test.cardapio;

import main.cardapio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LancheTest {

    @BeforeEach
    void setUp() {
        IngredienteFactory.limpar();
    }

    @Test
    void deveCriarLancheComPrecoCorreto() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false));

        Lanche lanche = new Lanche("X-Burger", 22.90f, ingredientes);

        assertEquals(22.90f, lanche.getPreco());
    }

    @Test
    void deveCriarLancheESomarCalorias() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false));

        Lanche lanche = new Lanche("X-Burger", 22.90f, ingredientes);

        assertEquals(400, lanche.getCalorias());
    }

    @Test
    void deveCriarLancheAPartirDeReceitaComDescricaoCorreta() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Tirolez – Queijo Cheddar Fatiado", 90, true));
        ReceitaLanche receita = new ReceitaLanche("X-Cheddar", ingredientes, 19.90f,
                10, TecnicaPreparo.TRADICIONAL);

        Lanche lanche = new Lanche(receita);

        assertEquals("X-Cheddar", lanche.getDescricao());
    }

    @Test
    void deveCriarLancheAPartirDeReceitaComPrecoCorreto() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Tirolez – Queijo Cheddar Fatiado", 90, true));
        ReceitaLanche receita = new ReceitaLanche("X-Cheddar", ingredientes, 19.90f,
                10, TecnicaPreparo.TRADICIONAL);

        Lanche lanche = new Lanche(receita);

        assertEquals(19.90f, lanche.getPreco());
    }

    @Test
    void deveManterMesmaInstanciaDeIngredienteAoCriarLancheAPartirDeReceita() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        Ingrediente queijo = IngredienteFactory.getIngrediente("Tirolez – Queijo Cheddar Fatiado", 90, true);
        ingredientes.add(queijo);
        ReceitaLanche receita = new ReceitaLanche("X-Cheddar", ingredientes, 19.90f,
                10, TecnicaPreparo.TRADICIONAL);

        Lanche lanche = new Lanche(receita);

        assertSame(queijo, lanche.getIngredientes().getFirst());
    }

    @Test
    void deveRetornarExcecaoParaReceitaNula() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Lanche((ReceitaLanche) null));
        assertEquals("ERR01 - A receita referenciada não pode ser nula!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaDescricaoNula() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Benassi – Alface Crespa", 5, false));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Lanche(null, 10.0f, ingredientes));
        assertEquals("ERR02 - A descrição do item não pode ser nula ou em branco!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaPrecoNegativo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Raizs – Tomate Orgânico", 18, false));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Lanche("X-Salada", -1.0f, ingredientes));
        assertEquals("ERR03 - O preço do lanche não pode ser negativo!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaListaIngredientesNula() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Lanche("X-Tudo", 25.0f, null));
        assertEquals("ERR02 - A lista de ingredientes do lanche não pode ser nula ou vazia!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaListaIngredientesVazia() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Lanche("X-Tudo", 25.0f, new ArrayList<>()));
        assertEquals("ERR02 - A lista de ingredientes do lanche não pode ser nula ou vazia!", e.getMessage());
    }

    @Test
    void deveRetornarListaDeIngredientesIndependenteDaOriginal() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Seara – Bacon em Fatias", 150, false));
        Lanche lanche = new Lanche("X-Bacon", 21.90f, ingredientes);

        lanche.getIngredientes().add(IngredienteFactory.getIngrediente("Mantiqueira – Ovos Brancos", 70, false));

        assertEquals(1, lanche.getIngredientes().size());
    }
}
