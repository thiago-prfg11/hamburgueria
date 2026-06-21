package test.atendimento;

import main.atendimento.EmbaladorPedido;
import main.atendimento.FabricaEmbalagem;
import main.atendimento.FabricaEmbalagemBalcao;
import main.atendimento.FabricaEmbalagemDelivery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmbaladorPedidoTest {

    @Test
    void deveEmbalarParaDelivery() {
        FabricaEmbalagem fabrica = new FabricaEmbalagemDelivery();
        EmbaladorPedido embalador = new EmbaladorPedido(fabrica);
        assertEquals("Sacola Térmica", embalador.getDescricaoEmbalagem());
    }

    @Test
    void deveEmbalarParaBalcao() {
        FabricaEmbalagem fabrica = new FabricaEmbalagemBalcao();
        EmbaladorPedido embalador = new EmbaladorPedido(fabrica);
        assertEquals("Bandeja", embalador.getDescricaoEmbalagem());
    }

    @Test
    void deveAplicarProtecaoParaDelivery() {
        FabricaEmbalagem fabrica = new FabricaEmbalagemDelivery();
        EmbaladorPedido embalador = new EmbaladorPedido(fabrica);
        assertEquals("Lacre de Segurança", embalador.getDescricaoProtecao());
    }

    @Test
    void deveAplicarProtecaoParaBalcao() {
        FabricaEmbalagem fabrica = new FabricaEmbalagemBalcao();
        EmbaladorPedido embalador = new EmbaladorPedido(fabrica);
        assertEquals("Guardanapo", embalador.getDescricaoProtecao());
    }

    @Test
    void deveRetornarExcecaoParaFabricaNula() {
        try {
            new EmbaladorPedido(null);
        } catch (IllegalArgumentException e) {
            assertEquals("A fábrica da embalagem inserida não pode ser nula!", e.getMessage());
        }
    }
}
