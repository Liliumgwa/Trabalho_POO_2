package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.*;
import service.GerenciamentoGrade;

public class SistemaAcademicoGUI extends JFrame {
    private GerenciamentoGrade coordenacao = new GerenciamentoGrade();
    private Map<String, String> gradeHorarios = new HashMap<>(); 
    private Usuario usuarioLogado = null;

    private JTabbedPane tabbedPane;
    private DefaultTableModel tableModelProfessores;
    private DefaultTableModel tableModelDisciplinas;
    private JTextArea txtGradeHorarios;
    
    private JLabel lblStatusLogin;
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnLoginLogout;

    // PALETA DE CORES DE ALTO CONTRASTE (Flat Dark / Clean)
    private final Color COLOR_BACKGROUND = new Color(24, 30, 41);  // Fundo bem escuro
    private final Color COLOR_SURFACE = new Color(37, 46, 62);     // Cards e painéis intermediários
    private final Color COLOR_PRIMARY = new Color(41, 128, 185);   // Azul destacado para cabeçalhos/botões
    private final Color COLOR_TEXT = new Color(255, 255, 255);     // BRANCO PURO para contraste máximo
    private final Color COLOR_TEXT_MUTED = new Color(180, 190, 210); // Cinza claro legível para labels secundárias
    private final Color COLOR_ACCENT = new Color(39, 174, 96);     // Verde sucesso
    private final Color COLOR_INPUT_BG = new Color(50, 63, 85);    // Fundo dos campos de texto e combos

    // Botões controláveis por nível de acesso
    private JButton btnCadastrarProf;
    private JButton btnEditarDisp;
    private JButton btnSalvarDisc;
    private JButton btnVincular;
    private JButton btnAlocar;
    private JPanel  painelCadastroProf;

    public SistemaAcademicoGUI() {
        setTitle("Sistema Acadêmico - Gestão de Horários e Professores");
        setSize(950, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BACKGROUND);

        inicializarDadosExemplo();
        setLayout(new BorderLayout(10, 10));

        criarBarraSuperior();

        // Estilização das abas (TabbedPane) para garantir contraste
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(COLOR_SURFACE);
        tabbedPane.setForeground(COLOR_TEXT); // Texto da aba em Branco
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabbedPane.addTab("Professores", criarPainelProfessores());
        tabbedPane.addTab("Disciplinas & Afinidades", criarPainelDisciplinas());
        tabbedPane.addTab("Grade de Horários", criarPainelGrade());

        add(tabbedPane, BorderLayout.CENTER);
        atualizarPermissoesInterface();
    }

    private void criarBarraSuperior() {
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        painelTopo.setBackground(COLOR_SURFACE);

        lblStatusLogin = new JLabel("Status: NÃO LOGADO");
        lblStatusLogin.setForeground(new Color(231, 76, 60)); // Vermelho vivo
        lblStatusLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));

        txtLogin = new JTextField(8);
        configurarCampoInput(txtLogin);
        
        txtSenha = new JPasswordField(8);
        configurarCampoInput(txtSenha);

        btnLoginLogout = new JButton("Entrar");
        configurarBotao(btnLoginLogout, COLOR_PRIMARY);

        painelTopo.add(lblStatusLogin);
        painelTopo.add(criarLabelForte("Usuário:"));
        painelTopo.add(txtLogin);
        painelTopo.add(criarLabelForte("Senha:"));
        painelTopo.add(txtSenha);
        painelTopo.add(btnLoginLogout);

        add(painelTopo, BorderLayout.NORTH);

        btnLoginLogout.addActionListener(e -> {
            if (usuarioLogado == null) {
                String login = txtLogin.getText();
                String senha = new String(txtSenha.getPassword());
                Usuario u = Usuario.realizarLogin(login, senha);
                if (u != null) {
                    usuarioLogado = u;
                    lblStatusLogin.setText("Logado como: " + u.getLogin().toUpperCase());
                    lblStatusLogin.setForeground(COLOR_ACCENT);
                    btnLoginLogout.setText("Sair");
                    txtLogin.setEnabled(false);
                    txtSenha.setEnabled(false);
                    atualizarPermissoesInterface();
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                usuarioLogado = null;
                lblStatusLogin.setText("Status: NÃO LOGADO");
                lblStatusLogin.setForeground(new Color(231, 76, 60));
                btnLoginLogout.setText("Entrar");
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                txtLogin.setText("");
                txtSenha.setText("");
                atualizarPermissoesInterface();
            }
        });
    }

    private JPanel criarPainelProfessores() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(COLOR_BACKGROUND);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"Nome", "Disponibilidade", "Competências (Disciplinas)"};
        tableModelProfessores = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(tableModelProfessores);
        configurarTabela(tabela);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(COLOR_SURFACE);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_INPUT_BG));
        painel.add(scroll, BorderLayout.CENTER);

        // Painel Lateral (Ações)
        JPanel painelLateral = new JPanel(new GridLayout(6, 1, 0, 10));
        painelLateral.setBackground(COLOR_BACKGROUND);
        painelLateral.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        btnEditarDisp = new JButton("Editar Disponibilidade");
        configurarBotao(btnEditarDisp, COLOR_PRIMARY);
        painelLateral.add(btnEditarDisp);

        // Espaçadores para empurrar o botão para o topo
        for (int i = 0; i < 5; i++) {
            JLabel lblSpacer = new JLabel("");
            painelLateral.add(lblSpacer);
        }
        painel.add(painelLateral, BorderLayout.EAST);

        btnEditarDisp.addActionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um professor na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (usuarioLogado == null || usuarioLogado.getNivelAcesso() != NivelAcesso.ADMINISTRADOR) {
                JOptionPane.showMessageDialog(this, "Apenas administradores podem editar a disponibilidade de professores.", "Bloqueado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Professor p = coordenacao.getProfessores().get(selectedRow);
            abrirDialogoEdicaoDisponibilidade(p);
        });

        painelCadastroProf = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        painelCadastroProf.setBackground(COLOR_SURFACE);
        painelCadastroProf.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY), "Cadastrar Novo Professor", 
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), COLOR_TEXT));

        JTextField txtNome = new JTextField(20);
        configurarCampoInput(txtNome);

        btnCadastrarProf = new JButton("Salvar Professor");
        configurarBotao(btnCadastrarProf, COLOR_ACCENT);

        painelCadastroProf.add(criarLabelForte("Nome do Professor:"));
        painelCadastroProf.add(txtNome);
        painelCadastroProf.add(btnCadastrarProf);

        painel.add(painelCadastroProf, BorderLayout.SOUTH);

        btnCadastrarProf.addActionListener(e -> {
            if (txtNome.getText().trim().isEmpty()) return;
            try {
                if (usuarioLogado == null || usuarioLogado.getNivelAcesso() != NivelAcesso.ADMINISTRADOR) {
                    throw new SecurityException("Apenas Administradores podem cadastrar professores.");
                }
                // Cria o professor com todas as disponibilidades por padrão
                List<String> todasDisp = obterTodosPeriodosSlots();
                Professor p = new Professor(txtNome.getText().trim(), null, todasDisp);
                coordenacao.cadastrarProfessor(p);
                atualizarTabelaProfessores();
                txtNome.setText("");
                JOptionPane.showMessageDialog(this, "Professor adicionado!");
            } catch (SecurityException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Bloqueado", JOptionPane.WARNING_MESSAGE);
            }
        });

        atualizarTabelaProfessores();
        return painel;
    }

    private List<String> obterTodosPeriodosSlots() {
        List<String> slots = new ArrayList<>();
        String[] periodos = {"07:30-09:10", "09:30-11:10", "19:00-20:40", "20:50-22:30"};
        for (DiaSemana d : DiaSemana.values()) {
            for (String per : periodos) {
                slots.add(d.name() + "|" + per);
            }
        }
        return slots;
    }

    private String obterResumoDisponibilidade(Professor p) {
        List<String> disp = p.getDisponibilidades();
        if (disp == null || disp.isEmpty()) {
            return "Nenhuma disponibilidade (Indisponível)";
        }
        int totalSlots = DiaSemana.values().length * 4; // 7 * 4 = 28
        if (disp.size() == totalSlots) {
            return "Disponível em todos os horários (28 slots)";
        }
        return disp.size() + " de " + totalSlots + " slots livres";
    }

    private void abrirDialogoEdicaoDisponibilidade(Professor professor) {
        JDialog dialog = new JDialog(this, "Editar Disponibilidade - " + professor.getNome(), true);
        dialog.setSize(750, 480);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(COLOR_BACKGROUND);
        dialog.setLayout(new BorderLayout(10, 10));

        // Título / Info
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelTitulo.setBackground(COLOR_SURFACE);
        JLabel lblTitulo = new JLabel("Selecione os períodos disponíveis para: " + professor.getNome());
        lblTitulo.setForeground(COLOR_TEXT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painelTitulo.add(lblTitulo);
        dialog.add(painelTitulo, BorderLayout.NORTH);

        // Grade de Checkboxes (7 dias x 4 períodos)
        String[] periodos = {"07:30-09:10", "09:30-11:10", "19:00-20:40", "20:50-22:30"};
        DiaSemana[] dias = DiaSemana.values();

        // 1 + 7 linhas (cabeçalho + dias), 1 + 4 colunas (nome do dia + períodos)
        JPanel painelGrid = new JPanel(new GridLayout(dias.length + 1, periodos.length + 1, 5, 5));
        painelGrid.setBackground(COLOR_BACKGROUND);
        painelGrid.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Cabeçalhos das colunas
        JLabel lblCanto = new JLabel("Dia / Horário", SwingConstants.CENTER);
        lblCanto.setForeground(COLOR_TEXT_MUTED);
        lblCanto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        painelGrid.add(lblCanto);

        for (String per : periodos) {
            JLabel lblPer = new JLabel(per, SwingConstants.CENTER);
            lblPer.setForeground(COLOR_TEXT);
            lblPer.setFont(new Font("Segoe UI", Font.BOLD, 11));
            painelGrid.add(lblPer);
        }

        // Checkboxes mapeados por chave: "DIA|PERIODO"
        Map<String, JCheckBox> checkboxes = new HashMap<>();

        for (DiaSemana dia : dias) {
            JLabel lblDia = new JLabel("  " + dia.name());
            lblDia.setForeground(COLOR_TEXT);
            lblDia.setFont(new Font("Segoe UI", Font.BOLD, 11));
            painelGrid.add(lblDia);

            for (String per : periodos) {
                String chave = dia.name() + "|" + per;
                JCheckBox cb = new JCheckBox();
                cb.setBackground(COLOR_SURFACE);
                cb.setHorizontalAlignment(SwingConstants.CENTER);
                
                // Se o professor já tem essa disponibilidade, marcar
                if (professor.temDisponibilidade(chave)) {
                    cb.setSelected(true);
                }
                
                checkboxes.put(chave, cb);
                painelGrid.add(cb);
            }
        }

        dialog.add(new JScrollPane(painelGrid), BorderLayout.CENTER);

        // Painel inferior de ações (Salvar / Cancelar)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        painelBotoes.setBackground(COLOR_SURFACE);

        JButton btnSalvar = new JButton("Salvar");
        configurarBotao(btnSalvar, COLOR_ACCENT);

        JButton btnCancelar = new JButton("Cancelar");
        configurarBotao(btnCancelar, COLOR_SURFACE);
        btnCancelar.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY));

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSalvar);
        dialog.add(painelBotoes, BorderLayout.SOUTH);

        btnCancelar.addActionListener(ev -> dialog.dispose());

        btnSalvar.addActionListener(ev -> {
            List<String> novasDisponibilidades = new ArrayList<>();
            checkboxes.forEach((chave, cb) -> {
                if (cb.isSelected()) {
                    novasDisponibilidades.add(chave);
                }
            });
            
            try {
                professor.setDisponibilidades(novasDisponibilidades, usuarioLogado);
                atualizarTabelaProfessores();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Disponibilidades de " + professor.getNome() + " atualizadas com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    private JPanel criarPainelDisciplinas() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 15, 15));
        painel.setBackground(COLOR_BACKGROUND);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // LADO ESQUERDO
        JPanel painelEsquerdo = new JPanel(new BorderLayout(10, 10));
        painelEsquerdo.setBackground(COLOR_SURFACE);
        painelEsquerdo.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY), "Disciplinas Cadastradas", 
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), COLOR_TEXT));
        
        String[] colunas = {"Código", "Nome", "Carga"};
        tableModelDisciplinas = new DefaultTableModel(colunas, 0);
        JTable tabelaDisc = new JTable(tableModelDisciplinas);
        configurarTabela(tabelaDisc);
        JScrollPane scrollDisc = new JScrollPane(tabelaDisc);
        scrollDisc.getViewport().setBackground(COLOR_SURFACE);
        painelEsquerdo.add(scrollDisc, BorderLayout.CENTER);

        JPanel formDisc = new JPanel(new GridLayout(4, 2, 8, 8));
        formDisc.setBackground(COLOR_SURFACE);
        formDisc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JTextField txtCod = new JTextField(); configurarCampoInput(txtCod);
        JTextField txtNomeD = new JTextField(); configurarCampoInput(txtNomeD);
        JTextField txtCarga = new JTextField(); configurarCampoInput(txtCarga);
        btnSalvarDisc = new JButton("Adicionar Disciplina");
        configurarBotao(btnSalvarDisc, COLOR_PRIMARY);

        formDisc.add(criarLabelForte("Código:")); formDisc.add(txtCod);
        formDisc.add(criarLabelForte("Nome:")); formDisc.add(txtNomeD);
        formDisc.add(criarLabelForte("Carga Horária (h):")); formDisc.add(txtCarga);
        formDisc.add(new JLabel("")); formDisc.add(btnSalvarDisc);
        painelEsquerdo.add(formDisc, BorderLayout.SOUTH);

        // LADO DIREITO
        JPanel painelDireito = new JPanel(new GridLayout(7, 1, 10, 10));
        painelDireito.setBackground(COLOR_SURFACE);
        painelDireito.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_ACCENT), "Vincular Afinidade de Professor", 
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), COLOR_TEXT));

        JComboBox<String> cbProfessores = new JComboBox<>(); configurarComponenteCombo(cbProfessores);
        JComboBox<String> cbDisciplinas = new JComboBox<>(); configurarComponenteCombo(cbDisciplinas);
        JComboBox<Preferencia> cbPreferencia = new JComboBox<>(Preferencia.values()); configurarComponenteCombo(cbPreferencia);
        
        btnVincular = new JButton("Gravar Nova Afinidade");
        configurarBotao(btnVincular, COLOR_ACCENT);

        painelDireito.add(criarLabelForte("1. Escolha o Professor:"));
        painelDireito.add(cbProfessores);
        painelDireito.add(criarLabelForte("2. Escolha a Disciplina:"));
        painelDireito.add(cbDisciplinas);
        painelDireito.add(criarLabelForte("3. Nível de Afinidade/Preferência:"));
        painelDireito.add(cbPreferencia);
        painelDireito.add(btnVincular);

        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 1) {
                cbProfessores.removeAllItems();
                for (Professor p : coordenacao.getProfessores()) cbProfessores.addItem(p.getNome());
                cbDisciplinas.removeAllItems();
                for (Disciplina d : coordenacao.getDisciplinas()) cbDisciplinas.addItem(d.getCodigo() + " - " + d.getNome());
            }
        });

        btnSalvarDisc.addActionListener(e -> {
            if (txtCod.getText().isEmpty() || txtNomeD.getText().isEmpty()) return;
            try {
                if (usuarioLogado == null
                        || (usuarioLogado.getNivelAcesso() != NivelAcesso.ADMINISTRADOR
                        && usuarioLogado.getNivelAcesso() != NivelAcesso.COORDENADOR)) {
                    throw new SecurityException("Apenas Administradores e Coordenadores podem cadastrar disciplinas.");
                }
                int ch = Integer.parseInt(txtCarga.getText());
                coordenacao.cadastrarDisciplinas(new Disciplina(txtCod.getText(), txtNomeD.getText(), ch));
                atualizarTabelaDisciplinas();
                txtCod.setText(""); txtNomeD.setText(""); txtCarga.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnVincular.addActionListener(e -> {
            if (coordenacao.getProfessores().isEmpty() || coordenacao.getDisciplinas().isEmpty()) return;
            try {
                Professor p = coordenacao.getProfessores().get(cbProfessores.getSelectedIndex());
                Disciplina d = coordenacao.getDisciplinas().get(cbDisciplinas.getSelectedIndex());
                Preferencia pref = (Preferencia) cbPreferencia.getSelectedItem();

                Competencia comp = new Competencia(d, pref);
                p.adicionarCompetencia(comp, usuarioLogado);

                atualizarTabelaProfessores();
                JOptionPane.showMessageDialog(this, "Afinidade salva com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Permissão", JOptionPane.ERROR_MESSAGE);
            }
        });

        painel.add(painelEsquerdo);
        painel.add(painelDireito);
        atualizarTabelaDisciplinas();
        return painel;
    }

    private JPanel criarPainelGrade() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(COLOR_BACKGROUND);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topoAgendamento = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        topoAgendamento.setBackground(COLOR_SURFACE);

        JComboBox<String> cbDias = new JComboBox<>(); configurarComponenteCombo(cbDias);
        for (DiaSemana d : DiaSemana.values()) cbDias.addItem(d.name());

        JComboBox<String> cbPeriodos = new JComboBox<>(); configurarComponenteCombo(cbPeriodos);
        cbPeriodos.addItem("07:30-09:10"); cbPeriodos.addItem("09:30-11:10");
        cbPeriodos.addItem("19:00-20:40"); cbPeriodos.addItem("20:50-22:30");

        JComboBox<String> cbProf = new JComboBox<>(); configurarComponenteCombo(cbProf);
        JComboBox<String> cbDisc = new JComboBox<>(); configurarComponenteCombo(cbDisc);

        btnAlocar = new JButton("Alocar Horário");
        configurarBotao(btnAlocar, COLOR_PRIMARY);

        topoAgendamento.add(criarLabelForte("Dia:")); topoAgendamento.add(cbDias);
        topoAgendamento.add(criarLabelForte("Período:")); topoAgendamento.add(cbPeriodos);
        topoAgendamento.add(criarLabelForte("Prof:")); topoAgendamento.add(cbProf);
        topoAgendamento.add(criarLabelForte("Matéria:")); topoAgendamento.add(cbDisc);
        topoAgendamento.add(btnAlocar);

        painel.add(topoAgendamento, BorderLayout.NORTH);

        txtGradeHorarios = new JTextArea();
        txtGradeHorarios.setEditable(false);
        txtGradeHorarios.setBackground(new Color(15, 21, 31)); // Fundo terminal super escuro
        txtGradeHorarios.setForeground(new Color(50, 255, 140)); // Verde neon de alto contraste
        txtGradeHorarios.setFont(new Font("Monospaced", Font.BOLD, 13));
        txtGradeHorarios.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        painel.add(new JScrollPane(txtGradeHorarios), BorderLayout.CENTER);

        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 2) {
                cbProf.removeAllItems();
                for (Professor p : coordenacao.getProfessores()) cbProf.addItem(p.getNome());
                cbDisc.removeAllItems();
                for (Disciplina d : coordenacao.getDisciplinas()) cbDisc.addItem(d.getCodigo());
            }
        });

        btnAlocar.addActionListener(e -> {
            if (coordenacao.getProfessores().isEmpty() || coordenacao.getDisciplinas().isEmpty()) return;
            
            String dia = (String) cbDias.getSelectedItem();
            String perStr = (String) cbPeriodos.getSelectedItem();
            Professor p = coordenacao.getProfessores().get(cbProf.getSelectedIndex());
            Disciplina d = coordenacao.getDisciplinas().get(cbDisc.getSelectedIndex());

            String chaveHorarioGeral = dia + "|" + perStr;

            if (usuarioLogado == null
                    || (usuarioLogado.getNivelAcesso() != NivelAcesso.ADMINISTRADOR
                    && usuarioLogado.getNivelAcesso() != NivelAcesso.COORDENADOR)) {
                JOptionPane.showMessageDialog(this, "Apenas Administradores e Coordenadores podem alocar horários.", "Acesso Negado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                coordenacao.alocarHorario(p, d, chaveHorarioGeral);

                String afinidade = "Nenhuma informada";
                if (p.getCompetencias().containsKey(d.getCodigo())) {
                    afinidade = p.getCompetencias().get(d.getCodigo()).getPreferencia().name();
                }

                String informacaoAula = String.format("Matéria: %s | Professor: %s (Afinidade: %s)", d.getCodigo(), p.getNome(), afinidade);
                gradeHorarios.put(chaveHorarioGeral, informacaoAula);
                String chaveProfessorOcupado = dia + "|" + perStr + "|" + p.getNome();
                gradeHorarios.put(chaveProfessorOcupado, informacaoAula);

                atualQuadroGradeTextual();
                JOptionPane.showMessageDialog(this, "Aula agendada com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Agendamento", JOptionPane.ERROR_MESSAGE);
            }
        });

        atualQuadroGradeTextual();
        return painel;
    }

    private void atualQuadroGradeTextual() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================================================================\n");
        sb.append("                                   QUADRO GERAL DE HORÁRIOS DA ACADEMIA                                 \n");
        sb.append("========================================================================================================\n\n");
        String[] periodos = {"07:30-09:10", "09:30-11:10", "19:00-20:40", "20:50-22:30"};
        for (DiaSemana d : DiaSemana.values()) {
            sb.append("▶ ").append(d.name()).append("\n");
            for (String per : periodos) {
                String chave = d.name() + "|" + per;
                sb.append("   [ ").append(per).append(" ] -> ");
                if (gradeHorarios.containsKey(chave)) sb.append(gradeHorarios.get(chave));
                else sb.append("Livre / Sem alocação");
                sb.append("\n");
            }
            sb.append("--------------------------------------------------------------------------------------------------------\n");
        }
        txtGradeHorarios.setText(sb.toString());
    }

    // MÉTODOS DE CONTROLE ESTÉTICO E AUTO-CONTRASTE DO SWING
    private JLabel criarLabelForte(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(COLOR_TEXT); // Texto sempre Branco Puro
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return label;
    }

    private void configurarCampoInput(JTextField campo) {
        campo.setBackground(COLOR_INPUT_BG);
        campo.setForeground(COLOR_TEXT); // Letras digitadas Brancas
        campo.setCaretColor(COLOR_TEXT); // Cursor Branco
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_INPUT_BG.brighter(), 1),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)));
    }

    private void configurarComponenteCombo(JComboBox<?> combo) {
        combo.setBackground(COLOR_INPUT_BG);
        combo.setForeground(COLOR_TEXT); // Texto visível do combo em Branco
        combo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        // Altera a lista interna suspensa do combo para não puxar fundo branco padrão
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    setBackground(COLOR_PRIMARY);
                    setForeground(COLOR_TEXT);
                } else {
                    setBackground(COLOR_INPUT_BG);
                    setForeground(COLOR_TEXT);
                }
                return c;
            }
        });
    }

    private void configurarBotao(JButton btn, Color background) {
        btn.setBackground(background);
        btn.setForeground(COLOR_TEXT);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void configurarTabela(JTable tabela) {
        tabela.setBackground(COLOR_SURFACE);
        tabela.setForeground(COLOR_TEXT);
        tabela.setGridColor(COLOR_BACKGROUND);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabela.setRowHeight(24);
        tabela.setSelectionBackground(COLOR_PRIMARY);
        tabela.setSelectionForeground(COLOR_TEXT);
        
        tabela.getTableHeader().setBackground(COLOR_PRIMARY);
        tabela.getTableHeader().setForeground(COLOR_TEXT);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void atualizarTabelaProfessores() {
        tableModelProfessores.setRowCount(0);
        for (Professor p : coordenacao.getProfessores()) {
            StringBuilder comps = new StringBuilder();
            p.getCompetencias().forEach((k, v) -> {
                comps.append(v.getDisciplina().getNome()).append(" (").append(v.getPreferencia()).append("); ");
            });
            tableModelProfessores.addRow(new Object[]{
                p.getNome(), 
                obterResumoDisponibilidade(p),
                comps.toString().isEmpty() ? "Nenhuma afinidade atribuída" : comps.toString()
            });
        }
    }

    private void atualizarTabelaDisciplinas() {
        tableModelDisciplinas.setRowCount(0);
        for (Disciplina d : coordenacao.getDisciplinas()) {
            tableModelDisciplinas.addRow(new Object[]{d.getCodigo(), d.getNome(), d.getCargaHoraria() + "h"});
        }
    }

    private void atualizarPermissoesInterface() {
        boolean logado       = usuarioLogado != null;
        boolean isAdmin      = logado && usuarioLogado.getNivelAcesso() == NivelAcesso.ADMINISTRADOR;
        boolean isCoord      = logado && usuarioLogado.getNivelAcesso() == NivelAcesso.COORDENADOR;
        boolean isProfessor  = logado && usuarioLogado.getNivelAcesso() == NivelAcesso.PROFESSOR;

        // --- Abas: visibilidade por papel ---
        // PROFESSOR: só vê Grade de Horários
        // COORDENADOR e ADMIN: vêem tudo
        tabbedPane.setEnabledAt(0, isAdmin || isCoord); // aba Professores
        tabbedPane.setEnabledAt(1, isAdmin || isCoord); // aba Disciplinas
        tabbedPane.setEnabledAt(2, logado);             // aba Grade (todos logados)

        // Se aba atual ficou bloqueada, redirecionar para Grade
        if (!tabbedPane.isEnabledAt(tabbedPane.getSelectedIndex())) {
            tabbedPane.setSelectedIndex(2);
        }

        // --- Aba Professores (somente ADMIN) ---
        if (btnCadastrarProf != null) btnCadastrarProf.setEnabled(isAdmin);
        if (btnEditarDisp    != null) btnEditarDisp.setEnabled(isAdmin);
        if (painelCadastroProf != null) painelCadastroProf.setEnabled(isAdmin);

        // --- Aba Disciplinas (ADMIN + COORDENADOR) ---
        if (btnSalvarDisc != null) btnSalvarDisc.setEnabled(isAdmin || isCoord);
        if (btnVincular   != null) btnVincular.setEnabled(isAdmin || isCoord);

        // --- Aba Grade (ADMIN + COORDENADOR podem alocar; PROFESSOR só lê) ---
        if (btnAlocar != null) btnAlocar.setEnabled(isAdmin || isCoord);
    }

    private void inicializarDadosExemplo() {
        coordenacao.cadastrarDisciplinas(new Disciplina("ALGO1", "Algoritmos I", 80));
        coordenacao.cadastrarDisciplinas(new Disciplina("POO", "Programação Orientada a Objetos", 60));
        coordenacao.cadastrarDisciplinas(new Disciplina("BD1", "Banco de Dados I", 60));
        
        List<String> todasDisp = obterTodosPeriodosSlots();
        coordenacao.cadastrarProfessor(new Professor("Dr. Alan Turing", null, new ArrayList<>(todasDisp)));
        coordenacao.cadastrarProfessor(new Professor("Dra. Ada Lovelace", null, new ArrayList<>(todasDisp)));
    }
}