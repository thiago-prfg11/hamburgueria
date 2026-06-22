package main.operacional;

public class GerenteAprovador extends AprovadorDesconto {

    public GerenteAprovador() {
        super(30.0f);
    }

    public String getDescricaoCargo() {
        return "Gerente";
    }
}
