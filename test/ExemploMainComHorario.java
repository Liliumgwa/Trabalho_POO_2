import domain.*;

import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {

        Periodo aula = new Periodo(
                LocalTime.of(8, 0),
                LocalTime.of(10, 30)
        );

        Horario horario =
                new Horario(DiaSemana.SEGUNDA, aula);

        System.out.println(horario);
    }
}
