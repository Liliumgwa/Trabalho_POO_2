public abstract class Disciplina {
    private String codigo;
    private String nome;
    private int cargaHoraria;


    public Disciplina(String codigo, String nome, int cargaHoraria){
        this.codigo = codigo;
        if(nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
        } else{
            throw new IllegalArgumentException("ERRO O NOME ESTA VAZIO OU FOI INFORMADO DE MANEIRA INCORRETA!");
        }
    //fazer validacao(pode ser na main(eu acho))
        this.cargaHoraria = cargaHoraria;
    }
    //getters
    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    //setters


    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    //validacoes





}


