package ideiaviniciusmatchdisciplinas;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

        private JTextArea resultado;
        private Grade grade;

        public TelaPrincipal() {

                grade = new Grade();

                setTitle("MatchDisciplinas");
                setSize(600, 400);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);

                JButton btnGerar = new JButton("Criar Dados de Exemplo");

                resultado = new JTextArea();
                resultado.setEditable(false);

                btnGerar.addActionListener(e -> gerarExemplo());

                setLayout(new BorderLayout());

                add(btnGerar, BorderLayout.NORTH);
                add(new JScrollPane(resultado), BorderLayout.CENTER);
        }

        private void gerarExemplo() {

                grade = new Grade();

                Professor joao = new Professor("João");
                joao.adicionarCompetencia("Java");
                joao.adicionarCompetencia("POO");

                Horario h1 = new Horario(
                                DiaSemana.SEGUNDA,
                                Periodo.NOITE);

                joao.adicionarDisponibilidade(h1);

                Disciplina poo = new Disciplina(
                                "INF101",
                                "Programação Orientada a Objetos",
                                60);

                Alocacao a1 = new Alocacao(
                                poo,
                                joao,
                                h1);

                grade.adicionarAlocacao(a1);

                Professor maria = new Professor("Maria");

                maria.adicionarCompetencia("Banco de Dados");

                Horario h2 = new Horario(
                                DiaSemana.TERCA,
                                Periodo.NOITE);

                maria.adicionarDisponibilidade(h2);

                Disciplina bd = new Disciplina(
                                "INF202",
                                "Banco de Dados",
                                60);

                Alocacao a2 = new Alocacao(
                                bd,
                                maria,
                                h2);

                grade.adicionarAlocacao(a2);

                atualizarTela();
        }

        private void atualizarTela() {

                StringBuilder sb = new StringBuilder();

                for (Alocacao a : grade.getAlocacoes()) {
                        sb.append(a).append("\n");
                }

                resultado.setText(sb.toString());
        }
}