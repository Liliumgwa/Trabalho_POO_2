package aaa;

public final class Horario {

    private final DiaSemana dia;
    private final Periodo periodo;

    public Horario(DiaSemana dia, Periodo periodo) {
        this.dia = dia;
        this.periodo = periodo;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    @Override
    public String toString() {
        return dia + " - " + periodo;
    }
}