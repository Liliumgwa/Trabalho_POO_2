package exception;

import model.Professor;
import model.Disciplina;

public class CompetenciaExcecao extends IllegalArgumentException {
    public CompetenciaExcecao() {
        super("Professor não possui competência para ministrar esta disciplina.");
    }

    public CompetenciaExcecao(String message) {
        super(message);
    }

    public CompetenciaExcecao(Professor professor, Disciplina disciplina) {
        super((professor != null ? professor.getNome() : "Professor")
                + " não possui competência para ministrar "
                + (disciplina != null ? disciplina.getNome() : "disciplina"));
    }
}
