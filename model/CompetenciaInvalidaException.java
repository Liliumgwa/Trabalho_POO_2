package exception;

java.lang.RuntimeException erro;

public class CompetenciaInvalidaException
        extends RuntimeException {

    public CompetenciaInvalidaException(
            String mensagem) {

        super(mensagem);
    }
}
