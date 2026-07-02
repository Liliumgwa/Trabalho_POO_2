package exception;

public class DisciplinaExcecao {

    private String codigo;
    private String nome;
    private int interesse;

    public Disciplina(String codigo, String nome, int interesse) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "Código inválido."
            );
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome da disciplina é obrigatório."
            );
        }

        if (interesse < 0) {
            throw new IllegalArgumentException(
                    "O interesse não pode ser negativo."
            );
        }

        this.codigo = codigo;
        this.nome = nome;
        this.interesse = interesse;
    }

    public String getNome() {
        return nome;
    }
}