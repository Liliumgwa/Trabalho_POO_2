import java.util.HashMap;
import java.util.Map;

public class Professor {

    private String nome;
    private Map<String, Integer> competencias;
    private boolean disponibilidade;

    public Professor() {
        this.competencias = new HashMap<>();
        this.disponibilidade = true;
    }

    public Professor(String nome, Map<String, Integer> competencias, boolean disponibilidade) {
        this.nome = nome;
        this.competencias = (competencias != null) ? competencias : new HashMap<>();
        this.disponibilidade = disponibilidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Map<String, Integer> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(Map<String, Integer> competencias) {
        this.competencias = competencias;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}