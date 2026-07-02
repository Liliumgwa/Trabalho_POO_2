package model;

public class Alocacao {
    private Professor professor;
    private Disciplina disciplina;
    private String horario;

    public Alocacao(Professor professor, Disciplina disciplina, String horario) {
        this.professor = professor;
        this.disciplina = disciplina;
        this.horario = horario;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
