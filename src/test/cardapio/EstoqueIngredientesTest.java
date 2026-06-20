package test.cardapio;

import main.cardapio.EstoqueIngredientes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstoqueIngredientesTest {

    EstoqueIngredientes estoque = new EstoqueIngredientes();

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
        try {
            estoque.registrarSaida("Maionese Especial", 5);
        } catch (IllegalArgumentException e) {
            assertEquals("O ingrediente selecionado não foi encontrado no estoque!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaSaidaComQuantidadeMaiorQueDisponivel() {
        estoque.registrarEntrada("Bacon em Cubos", 150, false, 10);
        try {
            estoque.registrarSaida("Bacon em Cubos", 20);
        } catch (IllegalArgumentException e) {
            assertEquals("A quantidade selecionada para baixa é inferior à quantidade em estoque!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaEntradaComQuantidadeInvalida() {
        try {
            estoque.registrarEntrada("Picles Fatiado", 4, false, 0);
        } catch (IllegalArgumentException e) {
            assertEquals("A quantidade inserida para o ingrediente deve ser maior que 0!", e.getMessage());
        }
    }
}
