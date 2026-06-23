package model;

public class Competencia {
    private Disciplina disciplina;
    private Preferencia preferencia;

    public Competencia() {
    }

    public Competencia(Disciplina disciplina, Preferencia preferencia) {
        this.disciplina = disciplina;
        this.preferencia = preferencia;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Preferencia getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(Preferencia preferencia) {
        this.preferencia = preferencia;
    }
}
