package service;

import model.*;
import exception.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciamentoGrade {

    private List<Professor> professores = new ArrayList<>();
    private List<Disciplina> disciplinas = new ArrayList<>();
    private List<Alocacao> listaAlocacao = new ArrayList<>();
    private int numAlocacoes;

    public List<Professor> getProfessores() {
        return professores;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public List<Alocacao> getAlocacoes() {
        return listaAlocacao;
    }

    public void cadastrarProfessor(Professor p) {
        professores.add(p);
    }

    public void cadastrarDisciplinas(Disciplina d) {
        disciplinas.add(d);
    }

    public void listarProfessores() {
        for (Professor p : professores) {
            System.out.println("Listando todos os professores!");
            System.out.println("Nome: " + p.getNome() + " Competencias: " + p.getCompetencias() + " Disponibilidade: " + p.getDisponibilidades());
        }
    }

    public void listarDisciplinas() {
        for (Disciplina d : disciplinas) {
            System.out.println("--Listando todas as disciplinas--");
            System.out.println("Codigo específico: " + d.getCodigo() + " Disciplina: " + d.getNome() + " Carga Horaria: " + d.getCargaHoraria());
        }
    }

    public void alocarHorario(Professor p, Disciplina d, String horario) {
        if (p == null || d == null || horario == null) {
            throw new AlocacaoExcecao("A alocação não pode conter dados nulos.");
        }

        // Validação de Disponibilidade (Dia e Período)
        if (!p.temDisponibilidade(horario)) {
            throw new HorarioExcecao("O professor " + p.getNome() + " está indisponível no horário " + horario.replace("|", " - ") + "!");
        }

        // Validação de Competência (Afinidade)
        if (!p.getCompetencias().containsKey(d.getCodigo())) {
            throw new CompetenciaExcecao(p, d);
        }

        // Validação de conflitos de Horário
        for (Alocacao b : listaAlocacao) {
            if (b.getProfessor().getNome().equals(p.getNome()) && b.getHorario().equals(horario)) {
                throw new HorarioExcecao("O professor " + p.getNome() + " já possui aula nesse horário (" + horario + ").");
            }

            if (b.getDisciplina().getCodigo().equals(d.getCodigo()) && b.getHorario().equals(horario)) {
                throw new HorarioExcecao("A disciplina " + d.getNome() + " já está sendo dada neste horário.");
            }
        }

        Alocacao a = new Alocacao(p, d, horario);
        listaAlocacao.add(a);
        numAlocacoes++;
    }

    public int getNumAlocacoes() {
        return numAlocacoes;
    }
}
