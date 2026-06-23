package test.cardapio;

import main.cardapio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CatalogoReceitasTest {

    @BeforeEach
    void setUp() {
        IngredienteFactory.limpar();
    }

    @Test
    void deveCadastrarEObterReceitaPeloNome() {
        CatalogoReceitas catalogo = new CatalogoReceitas();
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Tirolez – Queijo Coalho", 90, true));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Coalho", ingredientes, 17.90f,
                11, TecnicaPreparo.TRADICIONAL));

        ReceitaLanche receita = catalogo.obterReceita("X-Coalho");

        assertEquals("X-Coalho", receita.getNome());
    }

    @Test
    void deveCadastrarEObterReceitaComIngredientesCorretos() {
        CatalogoReceitas catalogo = new CatalogoReceitas();
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Tirolez – Queijo Coalho", 90, true));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Coalho", ingredientes, 17.90f,
                11, TecnicaPreparo.TRADICIONAL));

        ReceitaLanche receita = catalogo.obterReceita("X-Coalho");

        assertEquals(1, receita.getIngredientesBase().size());
    }

    @Test
    void deveRetornarCloneDiferenteACadaChamada() {
        CatalogoReceitas catalogo = new CatalogoReceitas();
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Tirolez – Queijo Parmesão Ralado", 40, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Parmesão", ingredientes, 19.90f,
                13, TecnicaPreparo.TRADICIONAL));

        ReceitaLanche receita1 = catalogo.obterReceita("X-Parmesão");
        ReceitaLanche receita2 = catalogo.obterReceita("X-Parmesão");

        assertNotSame(receita1, receita2);
    }

    @Test
    void deveManterReceitaOriginalInalteradaAposModificarClone() {
        CatalogoReceitas catalogo = new CatalogoReceitas();
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(IngredienteFactory.getIngrediente("Alface Crespa", 5, false));
        catalogo.cadastrarReceita(new ReceitaLanche("X-Salada Especial", ingredientes, 16.90f,
                10, TecnicaPreparo.TRADICIONAL));

        ReceitaLanche receitaPersonalizada = catalogo.obterReceita("X-Salada Especial");
        receitaPersonalizada.setPrecoBase(99.0f);

        ReceitaLanche receitaOriginal = catalogo.obterReceita("X-Salada Especial");

        assertEquals(16.90f, receitaOriginal.getPrecoBase());
    }

    @Test
    void deveRetornarExcecaoParaCadastrarReceitaNula() {
        CatalogoReceitas catalogo = new CatalogoReceitas();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> catalogo.cadastrarReceita(null));
        assertEquals("ERR01 - A receita referenciada não pode ser nula!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaObterReceitaInexistente() {
        CatalogoReceitas catalogo = new CatalogoReceitas();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> catalogo.obterReceita("Receita Fantasma"));
        assertEquals("ERR06 - A receita buscada não existe no sistema!", e.getMessage());
    }
}
