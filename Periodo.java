package domain;

import java.time.LocalTime;

public class Periodo {

    private LocalTime inicio;
    private LocalTime fim;

    public Periodo(LocalTime inicio, LocalTime fim) {

        if (fim.isBefore(inicio)) {
            throw new IllegalArgumentException(
                "O horário final não pode ser menor que o inicial."
            );
        }

        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFim() {
        return fim;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public void setFim(LocalTime fim) {
        this.fim = fim;
    }

    @Override
    public String toString() {
        return inicio + " - " + fim;
    }
}
