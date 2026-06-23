package model;

import java.time.LocalTime;

public class Periodo {
    private final LocalTime inicio;
    private final LocalTime fim;

    public Periodo(LocalTime inicio, LocalTime fim) {
        if (fim.isBefore(inicio)) {
            throw new IllegalArgumentException("O horário final não pode ser menor que o inicial.");
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

    @Override
    public String toString() {
        return inicio + " - " + fim;
    }
}
