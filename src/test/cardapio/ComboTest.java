package test.cardapio;

import main.cardapio.Acompanhamento;
import main.cardapio.Bebida;
import main.cardapio.Combo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComboTest {

    @Test
    void deveSomarPrecoDosItensSemDesconto() {
        Combo combo = new Combo("Combo Clássico", 0f);
        combo.addItem(new Bebida("Refrigerante", 6.0f, 140));
        combo.addItem(new Acompanhamento("Batata Média", 10.0f, 400));
        assertEquals(16.0f, combo.getPreco());
    }

    @Test
    void deveAplicarDescontoPercentualNoPreco() {
        Combo combo = new Combo("Combo Promocional", 10f);
        combo.addItem(new Bebida("Suco", 8.0f, 110));
        combo.addItem(new Acompanhamento("Batata Pequena", 7.0f, 300));
        assertEquals(13.5f, combo.getPreco());
    }

    @Test
    void deveSomarCaloriasDeTodosOsItens() {
        Combo combo = new Combo("Combo Família", 0f);
        combo.addItem(new Bebida("Refrigerante 2L", 12.0f, 600));
        combo.addItem(new Acompanhamento("Batata Família", 18.0f, 700));
        assertEquals(1300, combo.getCalorias());
    }

    @Test
    void deveConterOutroComboAninhadoESomarRecursivamente() {
        Combo comboInterno = new Combo("Combo Dupla", 0f);
        comboInterno.addItem(new Bebida("Refrigerante", 6.0f, 140));
        comboInterno.addItem(new Acompanhamento("Batata Média", 10.0f, 400));

        Combo comboExterno = new Combo("Combo Família Completo", 0f);
        comboExterno.addItem(comboInterno);
        comboExterno.addItem(new Acompanhamento("Sobremesa", 9.0f, 250));

        assertEquals(25.0f, comboExterno.getPreco());
        assertEquals(790, comboExterno.getCalorias());
    }

    @Test
    void deveRemoverItemDoCombo() {
        Bebida refrigerante = new Bebida("Refrigerante", 6.0f, 140);
        Combo combo = new Combo("Combo Simples", 0f);
        combo.addItem(refrigerante);
        combo.addItem(new Acompanhamento("Batata", 10.0f, 400));
        combo.removerItem(refrigerante);
        assertEquals(1, combo.getItens().size());
    }

    @Test
    void deveRetornarListaDeItensIndependenteDaOriginal() {
        Combo combo = new Combo("Combo Teste", 0f);
        combo.addItem(new Bebida("Suco", 7.0f, 100));
        combo.getItens().add(new Acompanhamento("Batata", 9.0f, 300));
        assertEquals(1, combo.getItens().size());
    }

    @Test
    void deveRetornarExcecaoParaItemNulo() {
        Combo combo = new Combo("Combo Teste", 0f);
        try {
            combo.addItem(null);
        } catch (IllegalArgumentException e) {
            assertEquals("O item a ser adicionado não pode ser nulo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaComboConterSiMesmo() {
        Combo combo = new Combo("Combo Recursivo", 0f);
        try {
            combo.addItem(combo);
        } catch (IllegalArgumentException e) {
            assertEquals("Um combo não pode conter a si mesmo!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaPrecoDeComboSemItens() {
        Combo combo = new Combo("Combo Vazio", 0f);
        try {
            combo.getPreco();
        } catch (IllegalStateException e) {
            assertEquals("Não é possível calcular o preço de um combo sem itens!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaDescontoMenorQueZero() {
        try {
            new Combo("Combo Inválido", -5f);
        } catch (IllegalArgumentException e) {
            assertEquals("O desconto no valor final do combo deve estar entre 0 e 100!", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoParaDescontoMaiorQueCem() {
        try {
            new Combo("Combo Inválido", 150f);
        } catch (IllegalArgumentException e) {
            assertEquals("O desconto no valor final do combo deve estar entre 0 e 100!", e.getMessage());
        }
    }
}
