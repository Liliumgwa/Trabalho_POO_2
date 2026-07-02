package exception;

public class CompetenciaExcecao(Professor professor,
                               Disciplina disciplina) {

    if (!professor.temCompetencia(disciplina)) {

        throw new IllegalArgumentException(
                professor.getNome()
                        + " não possui competência para ministrar "
                        + disciplina.getNome()
        );
    }
}