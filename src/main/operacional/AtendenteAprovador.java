package main.operacional;

public class AtendenteAprovador extends AprovadorDesconto {

    public AtendenteAprovador() {
        super(5.0f);
    }

    public String getDescricaoCargo() {
        return "Atendente";
    }
}
