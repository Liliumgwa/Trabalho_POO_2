import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import service.*;

import model.*;

public class Main {
    
    // Método para limpar a tela do terminal usando o comando do sistema
    public static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback usando códigos de escape ANSI caso ocorra algum erro
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void main(String[] args) {
        GerenciamentoGrade Coordenacao = new GerenciamentoGrade();
        Scanner scanner = new Scanner(System.in);
        Usuario usuarioLogado = null;

        limparTela();
        System.out.println("=== SISTEMA ACADÊMICO - GESTÃO DE PROFESSORES ===");

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n-------------------------------------------");
            if (usuarioLogado == null) {
                System.out.println("Status: NÃO LOGADO");
                System.out.println("1. Realizar Login");
                System.out.println("0. Sair");
            } else {
                System.out.println("Status: LOGADO COMO [" + usuarioLogado.getLogin() + " - " + usuarioLogado.getNivelAcesso() + "]");
                System.out.println("1. Professor");
                System.out.println("2. Cadastrar Disciplina");
                System.out.println("4. Visualizar Disciplinas");
                System.out.println("5. Atribuir Disciplina a Professor");
                System.out.println("6. Realizar Logout");
                System.out.println("0. Sair");
            }
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                limparTela();
            } catch (NumberFormatException e) {
                limparTela();
                System.out.println("[ERRO] Por favor, insira um número válido.");
                continue;
            }

            if (opcao == 0) {
                System.out.println("Encerrando o sistema. Até logo!");
                break;
            }

            if (usuarioLogado == null) {
                // Ações do usuário não logado
                if (opcao == 1) {
                    System.out.print("Digite o login: ");
                    String login = scanner.nextLine();
                    System.out.print("Digite a senha: ");
                    String senha = scanner.nextLine();

                    usuarioLogado = Usuario.realizarLogin(login, senha);
                    limparTela();
                    if (usuarioLogado == null) {
                        System.out.println("[ERRO] Login ou senha inválidos.");
                    } else {
                        System.out.println("[SUCESSO] Login efetuado com sucesso!");
                    }
                } else {
                    System.out.println("[ERRO] Opção inválida.");
                }
            } else {
                // Ações do usuário logado
                switch (opcao) {
                    case 1:
                        // Submenu Professor
                        int opcaoSubProf = -1;
                        while (opcaoSubProf != 0) {
                            System.out.println("\n--- SUBMENU PROFESSOR ---");
                            System.out.println("1. Cadastrar Professor");
                            System.out.println("2. Editar Professor");
                            System.out.println("3. Visualizar professores cadastradados"); //mexer aqui!!!
                            System.out.println("3. Visualizar Dados do Professor");
                            System.out.println("0. Voltar ao Menu Principal");
                            System.out.print("Escolha: ");
                            try {
                                opcaoSubProf = Integer.parseInt(scanner.nextLine());
                                limparTela();
                            } catch (NumberFormatException e) {
                                limparTela();
                                System.out.println("[ERRO] Por favor, insira um número válido.");
                                continue;
                            }

                            if (opcaoSubProf == 1) {
                                // Cadastrar Professor
                                if (usuarioLogado.getNivelAcesso() != NivelAcesso.ADMINISTRADOR) {
                                    System.out.println("[BLOQUEADO] Apenas usuários com nível ADMINISTRADOR podem cadastrar professores.");
                                } else {
                                    System.out.print("Digite o nome do professor: ");
                                    String nome = scanner.nextLine();
                                    System.out.println("Definir disponibilidade:");
                                    System.out.println("1 - Disponível");
                                    System.out.println("2 - Indisponível");
                                    System.out.print("Escolha: ");
                                    String dispCriar = scanner.nextLine();
                                    boolean disponibilidade = dispCriar.equals("1");

                                    Professor novoProf = new Professor(nome, null, disponibilidade);
                                    Coordenacao.cadastrarProfessor(novoProf); //MUDANÇA FEITA
                                    limparTela();
                                    System.out.println("[SUCESSO] Professor " + nome + " cadastrado com sucesso!");
                                }
                            } else if (opcaoSubProf == 2) {
                                // Editar Professor
                                if (usuarioLogado.getNivelAcesso() != NivelAcesso.ADMINISTRADOR) {
                                    System.out.println("[BLOQUEADO] Apenas usuários com nível ADMINISTRADOR podem editar professores.");
                                } else if (professores.isEmpty()) {
                                    System.out.println("[ERRO] Nenhum professor cadastrado ainda.");
                                } else {
                                    // Seleção do Professor
                                    System.out.println("\n--- SELECIONE O PROFESSOR PARA EDITAR ---");
                                    for (int i = 0; i < professores.size(); i++) {
                                        System.out.println((i + 1) + ". " + professores.get(i).getNome());
                                    }
                                    System.out.print("Escolha o professor pelo número: ");
                                    int profEditIndex;
                                    try {
                                        profEditIndex = Integer.parseInt(scanner.nextLine()) - 1;
                                        limparTela();
                                        if (profEditIndex < 0 || profEditIndex >= professores.size()) {
                                            throw new IndexOutOfBoundsException();
                                        }
                                    } catch (Exception e) {
                                        limparTela();
                                        System.out.println("[ERRO] Escolha inválida. Edição cancelada.");
                                        continue;
                                    }
                                    Professor profParaEditar = professores.get(profEditIndex);

                                    int opcaoSubEdit = -1;
                                    while (opcaoSubEdit != 0) {
                                        System.out.println("\n--- EDITAR PROFESSOR: " + profParaEditar.getNome() + " ---");
                                        System.out.println("1. Alterar Nome");
                                        System.out.println("2. Alterar Disponibilidade");
                                        System.out.println("0. Voltar ao Submenu Professor");
                                        System.out.print("Escolha: ");
                                        try {
                                            opcaoSubEdit = Integer.parseInt(scanner.nextLine());
                                            limparTela();
                                        } catch (NumberFormatException e) {
                                            limparTela();
                                            System.out.println("[ERRO] Por favor, insira um número válido.");
                                            continue;
                                        }

                                        if (opcaoSubEdit == 1) {
                                            System.out.print("Digite o novo nome para o professor: ");
                                            String novoNome = scanner.nextLine();
                                            limparTela();
                                            try {
                                                profParaEditar.setNome(novoNome, usuarioLogado);
                                                System.out.println("[SUCESSO] Nome do professor atualizado!");
                                            } catch (SecurityException e) {
                                                System.out.println("[BLOQUEADO] " + e.getMessage());
                                            }
                                        } else if (opcaoSubEdit == 2) {
                                            System.out.println("Definir disponibilidade:");
                                            System.out.println("1 - Disponível");
                                            System.out.println("2 - Indisponível");
                                            System.out.print("Escolha: ");
                                            String dispOpcao = scanner.nextLine();
                                            boolean novaDisp = dispOpcao.equals("1");
                                            limparTela();
                                            try {
                                                profParaEditar.setDisponibilidade(novaDisp, usuarioLogado);
                                                System.out.println("[SUCESSO] Disponibilidade atualizada!");
                                            } catch (SecurityException e) {
                                                System.out.println("[BLOQUEADO] " + e.getMessage());
                                            }
                                        } else if (opcaoSubEdit != 0) {
                                            System.out.println("[ERRO] Opção inválida.");
                                        }
                                    }
                                }//opcao nova 3!!


                            }   else if(opcaoSubProf ==3){
                                System.out.println("\n--Visualizando todos os professores--");

                            }
                            else if (opcaoSubProf == 4) {
                                // Visualizar Dados do Professor
                                if (professores.isEmpty()) {
                                    System.out.println("[ERRO] Nenhum professor cadastrado no sistema ainda.");
                                } else {
                                    System.out.println("\n=== LISTA DE PROFESSORES CADASTRADOS ===");
                                    for (int i = 0; i < professores.size(); i++) {
                                        Professor p = professores.get(i);
                                        System.out.println("\n-------------------------------------------");
                                        System.out.println("Professor #" + (i + 1) + ": " + p.getNome());
                                        System.out.println("Disponibilidade: " + (p.isDisponibilidade() ? "Disponível" : "Indisponível"));
                                        System.out.println("--- Competências / Disciplinas ---");
                                        if (p.getCompetencias().isEmpty()) {
                                            System.out.println("  Nenhuma disciplina atribuída a este professor.");
                                        } else {
                                            for (Competencia comp : p.getCompetencias().values()) {
                                                Disciplina d = comp.getDisciplina();
                                                System.out.println("  * [" + d.getCodigo() + "] " + d.getNome() + " (" + d.getCargaHoraria() + "h) -> Preferência: " + comp.getPreferencia());
                                            }
                                        }
                                    }
                                }
                            } else if (opcaoSubProf != 0) {
                                System.out.println("[ERRO] Opção inválida.");
                            }
                        }
                        break;

                    case 2:
                        // Cadastrar Disciplina
                        if (usuarioLogado.getNivelAcesso() != NivelAcesso.ADMINISTRADOR) {
                            System.out.println("[BLOQUEADO] Apenas usuários com nível ADMINISTRADOR podem cadastrar disciplinas.");
                            break;
                        }

                        System.out.print("Digite o código da disciplina: ");
                        String codigoDisc = scanner.nextLine();
                        System.out.print("Digite o nome da disciplina: ");
                        String nomeDisc = scanner.nextLine();
                        System.out.print("Digite a carga horária (em horas): ");
                        int cargaHoraria;
                        try {
                            cargaHoraria = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            limparTela();
                            System.out.println("[ERRO] Carga horária inválida. Cadastro cancelado.");
                            break;
                        }

                        Disciplina novaDisciplina = new Disciplina(codigoDisc, nomeDisc, cargaHoraria);
                        disciplinasCriadas.add(novaDisciplina);
                        limparTela();
                        System.out.println("[SUCESSO] Disciplina " + nomeDisc + " cadastrada com sucesso!");
                        break;

                    case 3:
                        // Atribuir Disciplina a Professor
                        if (usuarioLogado.getNivelAcesso() != NivelAcesso.ADMINISTRADOR) {
                            System.out.println("[BLOQUEADO] Apenas usuários com nível ADMINISTRADOR podem atribuir disciplinas.");
                            break;
                        }

                        if (professores.isEmpty()) {
                            System.out.println("[ERRO] Nenhum professor cadastrado ainda.");
                            break;
                        }

                        if (disciplinasCriadas.isEmpty()) {
                            System.out.println("[ERRO] Nenhuma disciplina cadastrada no sistema ainda.");
                            break;
                        }

                        // Seleção do Professor
                        System.out.println("\n--- SELECIONE O PROFESSOR ---");
                        for (int i = 0; i < professores.size(); i++) {
                            System.out.println((i + 1) + ". " + professores.get(i).getNome());
                        }
                        System.out.print("Escolha o professor pelo número: ");
                        int profIndex;
                        try {
                            profIndex = Integer.parseInt(scanner.nextLine()) - 1;
                            limparTela();
                            if (profIndex < 0 || profIndex >= professores.size()) {
                                throw new IndexOutOfBoundsException();
                            }
                        } catch (Exception e) {
                            limparTela();
                            System.out.println("[ERRO] Escolha inválida. Atribuição cancelada.");
                            break;
                        }
                        Professor professorEscolhido = professores.get(profIndex);

                        // Seleção da Disciplina
                        System.out.println("\n--- DISCIPLINAS DISPONÍVEIS ---");
                        for (int i = 0; i < disciplinasCriadas.size(); i++) {
                            Disciplina d = disciplinasCriadas.get(i);
                            System.out.println((i + 1) + ". Código: " + d.getCodigo() + " | Nome: " + d.getNome() + " | Carga: " + d.getCargaHoraria() + "h");
                        }
                        System.out.print("Escolha a disciplina pelo número: ");
                        int discIndex;
                        try {
                            discIndex = Integer.parseInt(scanner.nextLine()) - 1;
                            limparTela();
                            if (discIndex < 0 || discIndex >= disciplinasCriadas.size()) {
                                throw new IndexOutOfBoundsException();
                            }
                        } catch (Exception e) {
                            limparTela();
                            System.out.println("[ERRO] Escolha inválida. Atribuição cancelada.");
                            break;
                        }
                        Disciplina disciplinaEscolhida = disciplinasCriadas.get(discIndex);

                        // Seleção do Nível de Preferência
                        System.out.println("\n--- NÍVEIS DE PREFERÊNCIA ---");
                        Preferencia[] prefValores = Preferencia.values();
                        for (int i = 0; i < prefValores.length; i++) {
                            System.out.println((i + 1) + ". " + prefValores[i]);
                        }
                        System.out.print("Escolha a preferência pelo número: ");
                        int prefIndex;
                        try {
                            prefIndex = Integer.parseInt(scanner.nextLine()) - 1;
                            limparTela();
                            if (prefIndex < 0 || prefIndex >= prefValores.length) {
                                throw new IndexOutOfBoundsException();
                            }
                        } catch (Exception e) {
                            limparTela();
                            System.out.println("[ERRO] Escolha inválida. Atribuição cancelada.");
                            break;
                        }
                        Preferencia preferenciaEscolhida = prefValores[prefIndex];

                        Competencia novaCompetencia = new Competencia(disciplinaEscolhida, preferenciaEscolhida);
                        try {
                            professorEscolhido.adicionarCompetencia(novaCompetencia, usuarioLogado);
                            System.out.println("[SUCESSO] Disciplina atribuída como competência ao professor " + professorEscolhido.getNome() + "!");
                        } catch (SecurityException e) {
                            System.out.println("[BLOQUEADO] " + e.getMessage());
                        }
                        break;

                    case 4:
                        usuarioLogado = null;
                        System.out.println("[SUCESSO] Logout efetuado.");
                        break;

                    default:
                        System.out.println("[ERRO] Opção inválida.");
                        break;
                }
            }
        }
        scanner.close();
    }
}
