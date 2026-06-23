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
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 110, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 250, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Faixa Azul – Queijo Parmesão", 80, true));
        ReceitaLanche receita = new ReceitaLanche("X-Burger Tradicional", ingredientes, 19.90f,
                10, TecnicaPreparo.TRADICIONAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        assertEquals("Tostar e Adicionar Wickbold – Pão Brioche para Hambúrguer + Grelhar e Adicionar Swift" +
                " – Hambúrguer Angus 180g + " +
                "Adicionar Faixa Azul – Queijo Parmesão + Embalar em Papel Manteiga", preparo.prepararLanche());
    }

    @Test
    void devePrepararLancheArtesanalComMesmaTecnicaEIngredientesDiferentes() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 150, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 320, false));
        ingredientes.add(IngredienteFactory.getIngrediente("President – Queijo Cheddar", 110, true));
        ReceitaLanche receita = new ReceitaLanche("X-Burger Artesanal", ingredientes, 27.90f,
                14, TecnicaPreparo.ARTESANAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        assertEquals("Tostar e Adicionar Wickbold – Pão Brioche para Hambúrguer + Grelhar e Adicionar Swift" +
                " – Hambúrguer Angus 180g +" +
                " Adicionar President – Queijo Cheddar + Embalar em Papel Manteiga", preparo.prepararLanche());
    }

    @Test
    void deveIndicarAusenciaDeCamadasQuandoReceitaSoTemPaoEProteina() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false));
        ReceitaLanche receita = new ReceitaLanche("Tradicional da Casa", ingredientes, 18.0f,
                10, TecnicaPreparo.TRADICIONAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        assertEquals("Tostar e Adicionar Wickbold – Pão Brioche para Hambúrguer + Grelhar e Adicionar Swift" +
                " – Hambúrguer Angus 180g +" +
                " Sem Camadas Adicionais + Embalar em Papel Manteiga", preparo.prepararLanche());
    }

    @Test
    void deveFinalizarLancheComDescricaoDaReceita() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 130, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 300, false));
        ReceitaLanche receita = new ReceitaLanche("X-Gourmet", ingredientes, 32.90f,
                15, TecnicaPreparo.ARTESANAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        assertEquals("X-Gourmet", preparo.finalizar().getDescricao());
    }

    @Test
    void deveFinalizarLancheComPrecoDaReceita() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 130, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 300, false));
        ReceitaLanche receita = new ReceitaLanche("X-Gourmet", ingredientes, 32.90f,
                15, TecnicaPreparo.ARTESANAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        assertEquals(32.90f, preparo.finalizar().getPreco());
    }

    @Test
    void deveRetornarMesmoResultadoAoPrepararDuasVezes() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ingredientes.add(IngredienteFactory.getIngrediente("Swift – Hambúrguer Angus 180g", 280, false));
        ReceitaLanche receita = new ReceitaLanche("X-Top", ingredientes, 20.90f,
                12, TecnicaPreparo.TRADICIONAL);

        PreparoGrelhado preparo = new PreparoGrelhado(receita);

        assertEquals(preparo.prepararLanche(), preparo.prepararLanche());
    }

    @Test
    void deveRetornarExcecaoParaReceitaNula() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PreparoGrelhado(null));
        assertEquals("ERR01 - A receita referenciada não pode ser nula!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaReceitaComMenosDeDoisIngredientes() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Wickbold – Pão Brioche para Hambúrguer", 120, false));
        ReceitaLanche receita = new ReceitaLanche("X-Bread", ingredientes, 15.0f,
                8, TecnicaPreparo.TRADICIONAL);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new PreparoGrelhado(receita));
        assertEquals("ERR07 - A receita referenciada deve conter ao menos um pão e uma proteína!", e.getMessage());
    }
}
