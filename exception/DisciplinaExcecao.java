package exception;

public class DisciplinaExcecao extends IllegalArgumentException {
    public DisciplinaExcecao() {
        super("Disciplina inválida.");
    }

    public DisciplinaExcecao(String message) {
        super(message);
    }
}
