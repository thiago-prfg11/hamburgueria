package main.atendimento;

public class ServicoPedidoFactory {

    public static IServicoPedido obterServico(String modalidade) {
        Class classe = null;
        Object objeto = null;
        try {
            classe = Class.forName("main.atendimento.Servico" + modalidade);
            objeto = classe.newInstance();
        } catch (Exception ex) {
            throw new IllegalArgumentException("O serviço escolhido não existe!");
        }
        if (!(objeto instanceof IServicoPedido)) {
            throw new IllegalArgumentException("O serviço escolhido é inválido!");
        }
        return (IServicoPedido) objeto;
    }
}
