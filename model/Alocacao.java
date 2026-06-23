package model;


public class Alocacao {
    private Professor professor;
    private Disciplina disciplina;
    private String horario;


    public Alocacao(Professor p, Disciplina d, String horario){
        this.professor = p;
        this.disciplina = d;
        this.horario = horario;
    }
    //getters e setters, fazer a validacao tambem
    public Professor getProfessor() {
        return professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public String getHorario() {
        return horario;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
