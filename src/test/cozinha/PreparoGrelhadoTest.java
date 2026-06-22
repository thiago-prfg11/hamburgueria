package test.cozinha;

import main.cardapio.*;
import main.cozinha.PreparoGrelhado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class PreparoGrelhadoTest {

    @Test
    void devePrepararLancheTradicionalComEtapasCorretas() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão de Forma", 110, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Carne 120g", 250, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Queijo Prato", 80, true));
        ReceitaLanche receita = new ReceitaLanche("X-Burger Tradicional", ingredientes, 19.90f,
                10, TecnicaPreparo.TRADICIONAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        assertEquals("Tostar e Adicionar Pão de Forma + Grelhar e Adicionar Carne 120g + " +
                "Adicionar Queijo Prato + Embalar em Papel Manteiga", preparo.prepararLanche());
    }

    @Test
    void devePrepararLancheArtesanalComMesmaTecnicaEIngredientesDiferentes() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Australiano", 150, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Carne Angus 180g", 320, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Queijo Brie", 110, true));
        ReceitaLanche receita = new ReceitaLanche("X-Burger Artesanal", ingredientes, 27.90f,
                14, TecnicaPreparo.ARTESANAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        assertEquals("Tostar e Adicionar Pão Australiano + Grelhar e Adicionar Carne Angus 180g +" +
                " Adicionar Queijo Brie + Embalar em Papel Manteiga", preparo.prepararLanche());
    }

    @Test
    void deveIndicarAusenciaDeCamadasQuandoReceitaSoTemPaoEProteina() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Simples", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Carne Simples", 280, false));
        ReceitaLanche receita = new ReceitaLanche("X-Simples", ingredientes, 18.0f,
                10, TecnicaPreparo.TRADICIONAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        assertEquals("Tostar e Adicionar Pão Simples + Grelhar e Adicionar Carne Simples +" +
                " Sem Camadas Adicionais + Embalar em Papel Manteiga", preparo.prepararLanche());
    }

    @Test
    void deveFinalizarLancheComDadosDaReceita() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Ciabatta", 130, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Fraldinha Grelhada", 300, false));
        ReceitaLanche receita = new ReceitaLanche("X-Gourmet", ingredientes, 32.90f,
                15, TecnicaPreparo.ARTESANAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        Lanche lanche = preparo.finalizar();

        assertEquals("X-Gourmet", lanche.getDescricao());
        assertEquals(32.90f, lanche.getPreco());
    }

    @Test
    void deveRetornarMesmoResultadoAoPrepararDuasVezes() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Idempotente", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Carne Idempotente", 280, false));
        ReceitaLanche receita = new ReceitaLanche("X-Idempotente", ingredientes, 20.90f,
                12, TecnicaPreparo.TRADICIONAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        assertEquals(preparo.prepararLanche(), preparo.prepararLanche());
    }

    @Test
    void deveRetornarExcecaoParaReceitaNula() {
        try {
            new PreparoGrelhado(null);
        } catch (IllegalArgumentException e) {
            assertEquals("A receita não pode ser nula!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaReceitaComMenosDeDoisIngredientes() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Pão Solo", 120, false));
        ReceitaLanche receita = new ReceitaLanche("X-Incompleto", ingredientes, 15.0f,
                8, TecnicaPreparo.TRADICIONAL);

        try {
            new PreparoGrelhado(receita);
        } catch (IllegalArgumentException e) {
            assertEquals("A receita deve conter ao menos um pão e uma proteína!", e.getMessage());
        }
    }
}
