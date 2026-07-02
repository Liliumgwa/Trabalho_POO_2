package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Professor {
    private String nome;
    private Map<String, Competencia> competencias;
    private List<String> disponibilidades;

    public Professor() {
        this.competencias = new HashMap<>();
        this.disponibilidades = new ArrayList<>();
    }

    public Professor(String nome, Map<String, Competencia> competencias, List<String> disponibilidades) {
        this.nome = nome;
        this.competencias = (competencias != null) ? competencias : new HashMap<>();
        this.disponibilidades = (disponibilidades != null) ? disponibilidades : new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome, Usuario usuario) {
        validarAcessoAdmin(usuario);
        this.nome = nome;
    }

    public Map<String, Competencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(Map<String, Competencia> competencias, Usuario usuario) {
        validarAcessoAdmin(usuario);
        this.competencias = competencias;
    }

    public List<String> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(List<String> disponibilidades, Usuario usuario) {
        validarAcessoAdmin(usuario);
        this.disponibilidades = disponibilidades;
    }

    public boolean temDisponibilidade(String periodo) {
        return disponibilidades != null && disponibilidades.contains(periodo);
    }

    public void adicionarCompetencia(Competencia competencia, Usuario usuario) {
        validarAcessoCoordenador(usuario);
        if (competencia != null && competencia.getDisciplina() != null) {
            this.competencias.put(competencia.getDisciplina().getCodigo(), competencia);
        }
    }

    private void validarAcessoAdmin(Usuario usuario) {
        if (usuario == null || usuario.getNivelAcesso() != NivelAcesso.ADMINISTRADOR) {
            throw new SecurityException("Acesso negado: Apenas Administradores podem alterar dados do professor.");
        }
    }

    private void validarAcessoCoordenador(Usuario usuario) {
        if (usuario == null || (usuario.getNivelAcesso() != NivelAcesso.ADMINISTRADOR
                && usuario.getNivelAcesso() != NivelAcesso.COORDENADOR)) {
            throw new SecurityException("Acesso negado: Apenas Administradores e Coordenadores podem executar esta ação.");
        }
    }
}

