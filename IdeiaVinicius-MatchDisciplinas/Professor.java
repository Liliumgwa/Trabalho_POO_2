package aaa;

import java.util.HashSet;
import java.util.Set;

public class Professor {

    private String nome;
    private Set<String> competencias;
    private Set<Horario> disponibilidade;

    public Professor(String nome) {
        this.nome = nome;
        this.competencias = new HashSet<>();
        this.disponibilidade = new HashSet<>();
    }

    public void adicionarCompetencia(String competencia) {
        competencias.add(competencia);
    }

    public void adicionarDisponibilidade(Horario horario) {
        disponibilidade.add(horario);
    }

    public String getNome() {
        return nome;
    }

    public Set<String> getCompetencias() {
        return competencias;
    }

    public Set<Horario> getDisponibilidade() {
        return disponibilidade;
    }

    @Override
    public String toString() {
        return nome;
    }
}