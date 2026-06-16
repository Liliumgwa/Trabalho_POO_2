package aaa;

import java.util.ArrayList;
import java.util.List;

public class Grade {

    private List<Alocacao> alocacoes;

    public Grade() {
        alocacoes = new ArrayList<>();
    }

    public void adicionarAlocacao(Alocacao alocacao) {
        alocacoes.add(alocacao);
    }

    public List<Alocacao> getAlocacoes() {
        return alocacoes;
    }
}