package test.cardapio;

import main.cardapio.EstoqueIngredientes;
import main.cardapio.IngredienteFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstoqueIngredientesTest {

    EstoqueIngredientes estoque;

    @BeforeEach
    void setUp() {
        IngredienteFactory.limpar();
        estoque = new EstoqueIngredientes();
    }

    @Test
    void deveRegistrarEntradaERetornarQuantidadeDisponivel() {
        estoque.registrarEntrada("Tomate", 18, false, 50);
        assertEquals(50, estoque.getQuantidadeDisponivel("Tomate"));
    }

    @Test
    void deveSomarQuantidadeEmEntradasSucessivasDoMesmoIngrediente() {
        estoque.registrarEntrada("Alface Americana", 5, false, 30);
        estoque.registrarEntrada("Alface Americana", 5, false, 20);
        assertEquals(50, estoque.getQuantidadeDisponivel("Alface Americana"));
    }

    @Test
    void deveRetornarZeroParaIngredienteNaoCadastrado() {
        assertEquals(0, estoque.getQuantidadeDisponivel("Picles"));
    }

    @Test
    void deveRegistrarSaidaEDiminuirQuantidade() {
        estoque.registrarEntrada("Cheddar Fatiado", 60, true, 40);
        estoque.registrarSaida("Cheddar Fatiado", 15);
        assertEquals(25, estoque.getQuantidadeDisponivel("Cheddar Fatiado"));
    }

    @Test
    void deveRetornarExcecaoParaSaidaDeIngredienteNaoCadastrado() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarSaida("Maionese Especial", 5));
        assertEquals("ERR06 - O ingrediente buscado não foi encontrado no estoque!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaSaidaComQuantidadeMaiorQueDisponivel() {
        estoque.registrarEntrada("Bacon em Cubos", 150, false, 10);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarSaida("Bacon em Cubos", 20));
        assertEquals("ERR10 - A quantidade selecionada para baixa é inferior à quantidade em estoque!", e.getMessage());
    }

    @Test
    void deveRetornarExcecaoParaEntradaComQuantidadeInvalida() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarEntrada("Picles Fatiado", 4, false, 0));
        assertEquals("ERR04 - A quantidade inserida para o ingrediente deve ser maior que 0!", e.getMessage());
    }

    @Test
    void deveRetornarZeroParaIngredienteExistenteNoFlyweightMasNaoNesteEstoque() {
        IngredienteFactory.getIngrediente("Cebola Roxa", 12, false);

        assertEquals(0, estoque.getQuantidadeDisponivel("Cebola Roxa"));
    }

    @Test
    void deveRetornarExcecaoParaSaidaDeIngredienteExistenteNoFlyweightMasNaoNesteEstoque() {
        IngredienteFactory.getIngrediente("Pimentao Verde", 9, false);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarSaida("Pimentao Verde", 1));
        assertEquals("ERR06 - O ingrediente buscado não foi encontrado no estoque!", e.getMessage());
    }
}
