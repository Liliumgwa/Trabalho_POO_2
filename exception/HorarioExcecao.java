package exception;

public class HorarioExcecao extends IllegalStateException {
    public HorarioExcecao() {
        super("O professor já possui aula nesse horário.");
    }

    public HorarioExcecao(String message) {
        super(message);
    }
}
