package exception;

public class AlocacaoExcecao(Alocacao alocacao) {

    if (alocacao == null) {
        throw new IllegalArgumentException(
                "A alocação não pode ser nula."
        );
    }

    alocacoes.add(alocacao);
}