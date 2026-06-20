package test.cardapio;

import main.cardapio.Ingrediente;
import main.cardapio.IngredienteFactory;
import main.cardapio.Lanche;
import main.cardapio.ReceitaLanche;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LancheTest {

    @Test
    void deveCriarLancheComIngredientesESomarCalorias() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Brioche Composite", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Carne Angus Composite", 280, false));
        Lanche lanche = new Lanche("X-Burger", 22.90f, ingredientes);
        assertEquals(22.90f, lanche.getPreco());
        assertEquals(400, lanche.getCalorias());
    }

    @Test
    void deveCriarLancheAPartirDeReceitaMantendoMesmaInstanciaDeIngrediente() throws CloneNotSupportedException {
        List<Ingrediente> ingredientes = new ArrayList<>();
        Ingrediente queijo = IngredienteFactory.getIngrediente("Queijo Cheddar Composite", 90, true);
        ingredientes.add(queijo);
        ReceitaLanche receita = new ReceitaLanche("X-Cheddar", ingredientes, 19.90f, 10);
        Lanche lanche = new Lanche(receita.clone());
        assertEquals("X-Cheddar", lanche.getDescricao());
        assertEquals(19.90f, lanche.getPreco());
        assertSame(queijo, lanche.getIngredientes().get(0));
    }

    @Test
    void deveRetornarExcecaoParaReceitaNula() {
        try {
            new Lanche((ReceitaLanche) null);
        } catch (IllegalArgumentException e) {
            assertEquals("A receita fornecida não pode ser nula!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaDescricaoNula() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Alface Composite", 5, false));
        try {
            new Lanche(null, 10.0f, ingredientes);
        } catch (IllegalArgumentException e) {
            assertEquals("A descrição do item é inválida!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaPrecoNegativo() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Tomate Composite", 18, false));
        try {
            new Lanche("X-Salada", -1.0f, ingredientes);
        } catch (IllegalArgumentException e) {
            assertEquals("O preço do lanche não pode ser negativo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaListaIngredientesNula() {
        try {
            new Lanche("X-Tudo", 25.0f, null);
        } catch (IllegalArgumentException e) {
            assertEquals("A lista de ingredientes do lanche não pode ser nula ou vazia!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaListaIngredientesVazia() {
        try {
            new Lanche("X-Tudo", 25.0f, new ArrayList<>());
        } catch (IllegalArgumentException e) {
            assertEquals("A lista de ingredientes do lanche não pode ser nula ou vazia!", e.getMessage());
        }
    }

    @Test
    void deveRetornarListaDeIngredientesIndependenteDaOriginal() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Bacon Composite", 150, false));
        Lanche lanche = new Lanche("X-Bacon", 21.90f, ingredientes);
        lanche.getIngredientes().add(IngredienteFactory.getIngrediente("Ovo Composite", 70, false));
        assertEquals(1, lanche.getIngredientes().size());
    }
}
