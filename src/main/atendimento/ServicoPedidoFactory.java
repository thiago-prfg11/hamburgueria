package main.atendimento;

public class ServicoPedidoFactory {

    public static IServicoPedido obterServico(String modalidade) {
        Class classe;
        Object objeto;
        try {
            classe = Class.forName("main.atendimento.Servico" + modalidade);
            objeto = classe.newInstance();
        } catch (Exception ex) {
            throw new IllegalArgumentException("ERR06 - O serviço referenciado não existe no sistema!");
        }
        if (!(objeto instanceof IServicoPedido)) {
            throw new IllegalArgumentException("ERR07 - O serviço referenciado é inválido!");
        }
        return (IServicoPedido) objeto;
    }
}
