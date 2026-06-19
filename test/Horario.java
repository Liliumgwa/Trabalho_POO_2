package domain;

public class Horario {

    private DiaSemana dia;
    private Periodo periodo;

    public Horario(DiaSemana dia, Periodo periodo) {
        this.dia = dia;
        this.periodo = periodo;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return dia + " | " + periodo;
    }
}
