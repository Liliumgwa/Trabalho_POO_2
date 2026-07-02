package exception;

public class HorarioExcecao(Alocacao nova) {

    for (Alocacao atual : alocacoes) {

        if (atual.getProfessor().equals(nova.getProfessor())
                && atual.getHorario().equals(nova.getHorario())) {

            throw new IllegalStateException(
                    "O professor já possui aula nesse horário."
            );
        }
    }
}