package test.pedido;

import main.cardapio.Bebida;
import main.cardapio.ItemCardapio;
import main.pedido.PedidoSnapshot;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PedidoSnapshotTest {

    @Test
    void deveManterCopiaIndependenteDaListaOriginal() {
        List<ItemCardapio> itensOriginais = new ArrayList<>();
        itensOriginais.add(new Bebida("Refrigerante", 6.0f, 140));
        PedidoSnapshot snapshot = new PedidoSnapshot(itensOriginais);
        itensOriginais.add(new Bebida("Suco", 7.0f, 90));
        assertEquals(1, snapshot.getItens().size());
    }

    @Test
    void deveRetornarExcecaoParaItensNulos() {
        try {
            new PedidoSnapshot(null);
        } catch (IllegalArgumentException e) {
            assertEquals("A lista de itens da snapshot não pode ser nula!", e.getMessage());
        }
    }
}
