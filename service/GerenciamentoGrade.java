package service;
import model.*;

import java.util.ArrayList;
import java.util.List;



public class GerenciamentoGrade{

    //Lista de todos os professores e disciplinas ja cadastradas
    private List<Professor> professores =  new ArrayList<>();
    private List<Disciplina> disciplinas = new ArrayList<>();
    private List<Alocacao> Listalocacao =  new ArrayList<>();

    //numeros de alocacoes para fins de "Boniteza"
    int numAlocacoes;

    public void cadastrarProfessor(Professor p){
        professores.add(p);
    }
    public void cadastrarDisciplinas(Disciplina d){
        disciplinas.add(d);
    }

    public void listarProfessores(){
        for(Professor p: professores ){
            System.out.println("Listando todos os professores!");
            System.out.println("Nome: " + p.getNome() + "Competencias: " + p.getCompetencias() + "Disponibilidade: " + p.isDisponibilidade());
        }
    }
    public void listarDisciplinas(){
    for(Disciplina d: disciplinas) {
        System.out.println("--Listando todas as disciplinas--");
        System.out.println("Codigo específico: " + d.getCodigo() + "Disciplina: " + d.getNome() + "Carga Horaria: " + d.getCargaHoraria());
    }

    }

    public boolean alocarHorario(Professor p, Disciplina d, String Horario, int numAlocacoes){
        for(Alocacao b : Listalocacao){

            // 1. Corrigido para comparar o Horário desejado
            if(b.getProfessor().getNome().equals(p.getNome()) && b.getHorario().equals(Horario)){
                System.out.println("ERRO: Professor já tem aula nesse horario!");
                return false;
            }

            // 2. Corrigido para comparar o Horário desejado
            if(b.getDisciplina().getCodigo().equals(d.getCodigo()) && b.getHorario().equals(Horario)){
                System.out.println("ERRO: Essa disciplina já esta sendo dada neste horário!");
                return false;
            }
        }

        //
        Alocacao a = new Alocacao(p, d, Horario);
        Listalocacao.add(a);

        System.out.println("Sucesso! Alocação realizada.");
        numAlocacoes++;
        return true;
    }


}


