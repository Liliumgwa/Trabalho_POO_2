package exception;

public class AlocacaoExcecao extends IllegalArgumentException {
    public AlocacaoExcecao() {
        super("A alocação não pode ser nula.");
    }

    public AlocacaoExcecao(String message) {
        super(message);
    }
}
