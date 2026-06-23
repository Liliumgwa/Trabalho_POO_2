package model;

import java.util.HashMap;
import java.util.Map;

public class Professor {
    private String nome;
    private Map<String, Competencia> competencias;
    private boolean disponibilidade;

    public Professor() {
        this.competencias = new HashMap<>();
    }

    public Professor(String nome, Map<String, Competencia> competencias, boolean disponibilidade) {
        this.nome = nome;
        this.competencias = (competencias != null) ? competencias : new HashMap<>();
        this.disponibilidade = disponibilidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome, Usuario usuario) {
        validarAcesso(usuario);
        this.nome = nome;
    }

    public Map<String, Competencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(Map<String, Competencia> competencias, Usuario usuario) {
        validarAcesso(usuario);
        this.competencias = competencias;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade, Usuario usuario) {
        validarAcesso(usuario);
        this.disponibilidade = disponibilidade;
    }

    public void adicionarCompetencia(Competencia competencia, Usuario usuario) {
        validarAcesso(usuario);
        if (competencia != null && competencia.getDisciplina() != null) {
            this.competencias.put(competencia.getDisciplina().getCodigo(), competencia);
        }
    }

    private void validarAcesso(Usuario usuario) {
        if (usuario == null || usuario.getNivelAcesso() != NivelAcesso.ADMINISTRADOR) {
            throw new SecurityException("Acesso negado: Apenas usuários com nível de acesso ADMINISTRADOR podem alterar os dados do professor.");
        }
    }
}

