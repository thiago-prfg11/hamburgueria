package main.configuracao;

public class ConfiguracaoHamburgueria {

    private ConfiguracaoHamburgueria() {}
    private static final ConfiguracaoHamburgueria instance = new ConfiguracaoHamburgueria();
    public static ConfiguracaoHamburgueria getInstance() {
        return instance;
    }

    private String nomeLoja;
    private float taxaEntregaPadrao;
    private String horarioAbertura;
    private String horarioFechamento;
    private String operadorLogado;
    private float alcadaMaximaAtendente;

    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        if (nomeLoja == null || nomeLoja.isBlank()) {
            throw new IllegalArgumentException("ERR02 - O nome da loja não pode ser nulo ou em branco!");
        }
        this.nomeLoja = nomeLoja;
    }

    public float getTaxaEntregaPadrao() {
        return taxaEntregaPadrao;
    }

    public void setTaxaEntregaPadrao(float taxaEntregaPadrao) {
        if (taxaEntregaPadrao < 0) {
            throw new IllegalArgumentException("ERR03 - A taxa de entrega não pode ser negativa!");
        }
        this.taxaEntregaPadrao = taxaEntregaPadrao;
    }

    public String getHorarioAbertura() {
        return horarioAbertura;
    }

    public void setHorarioAbertura(String horarioAbertura) {
        if (horarioAbertura == null || horarioAbertura.isBlank()) {
            throw new IllegalArgumentException("ERR02 - O horário de abertura não pode ser nulo ou em branco!");
        }
        this.horarioAbertura = horarioAbertura;
    }

    public String getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(String horarioFechamento) {
        if (horarioFechamento == null || horarioFechamento.isBlank()) {
            throw new IllegalArgumentException("ERR02 - O horário de fechamento não pode ser nulo ou em branco!");
        }
        this.horarioFechamento = horarioFechamento;
    }

    public String getOperadorLogado() {
        return operadorLogado;
    }

    public void setOperadorLogado(String operadorLogado) {
        if (operadorLogado == null || operadorLogado.isBlank()) {
            throw new IllegalArgumentException("ERR02 - O operador atual não pode ser nulo ou em branco!");
        }
        this.operadorLogado = operadorLogado;
    }

    public float getAlcadaMaximaAtendente() {
        return alcadaMaximaAtendente;
    }

    public void setAlcadaMaximaAtendente(float alcadaMaximaAtendente) {
        if (alcadaMaximaAtendente < 0) {
            throw new IllegalArgumentException("ERR03 - A alçada máxima não pode ser negativa!");
        }
        this.alcadaMaximaAtendente = alcadaMaximaAtendente;
    }
}
