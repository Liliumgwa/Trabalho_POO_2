package model;

public class Usuario {
    private String login;
    private String senha;
    private NivelAcesso nivelAcesso;

    public Usuario() {
    }

    public Usuario(String login, String senha, NivelAcesso nivelAcesso) {
        this.login = login;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
    }

    // Verifica se a senha fornecida corresponde à senha deste usuário
    public boolean verificarSenha(String senhaDigitada) {
        return this.senha != null && this.senha.equals(senhaDigitada);
    }

    // Simula a autenticação e retorna um Usuario correspondente com o nível de acesso caso login e senha estejam corretos
    public static Usuario realizarLogin(String loginDigitado, String senhaDigitada) {
        if ("admin".equalsIgnoreCase(loginDigitado) && "admin123".equals(senhaDigitada)) {
            return new Usuario("admin", "admin123", NivelAcesso.ADMINISTRADOR);
        } else if ("coordenador".equalsIgnoreCase(loginDigitado) && "coord123".equals(senhaDigitada)) {
            return new Usuario("coordenador", "coord123", NivelAcesso.COORDENADOR);
        } else if ("professor".equalsIgnoreCase(loginDigitado) && "prof123".equals(senhaDigitada)) {
            return new Usuario("professor", "prof123", NivelAcesso.PROFESSOR);
        }
        return null; // Login ou senha inválidos
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
