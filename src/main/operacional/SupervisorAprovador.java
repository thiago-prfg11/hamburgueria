package main.operacional;

public class SupervisorAprovador extends AprovadorDesconto {

    public SupervisorAprovador() {
        super(15.0f);
    }

    public String getDescricaoCargo() {
        return "Supervisor";
    }
}
