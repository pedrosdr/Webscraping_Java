package data;

public class DataRow
{
    // fields
    private String codigo;
    private String ano;
    private String etapa;
    private String disciplina;
    private String percentual;
    private String nivel;

    // constructors
    public DataRow(String codigo, String ano, String etapa, String disciplina, String percentual, String nivel)
    {
        this.codigo = codigo;
        this.ano = ano;
        this.etapa = etapa;
        this.disciplina = disciplina;
        this.percentual = percentual;
        this.nivel = nivel;
    }

    public DataRow() {}

    // properties

    public String getAno() {
        return ano;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getEtapa() {
        return etapa;
    }

    public String getNivel() {
        return nivel;
    }

    public String getPercentual() {
        return percentual;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setPercentual(String percentual) {
        this.percentual = percentual;
    }

    // methods
    @Override
    public String toString()
    {
        return String.format("[%s   %s   %s   %s   %s   %s]", codigo, ano, etapa, disciplina, percentual, nivel);
    }
}
