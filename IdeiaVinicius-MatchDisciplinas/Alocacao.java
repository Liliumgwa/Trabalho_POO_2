package aaa;

public class Alocacao {

    private Disciplina disciplina;
    private Professor professor;
    private Horario horario;

    public Alocacao(Disciplina disciplina,
                    Professor professor,
                    Horario horario) {

        this.disciplina = disciplina;
        this.professor = professor;
        this.horario = horario;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Horario getHorario() {
        return horario;
    }

    @Override
    public String toString() {

        return disciplina.getNome()
                + " -> "
                + professor.getNome()
                + " ("
                + horario
                + ")";
    }
}