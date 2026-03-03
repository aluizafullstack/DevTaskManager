package model.entities;

import model.contexto.AplicacaoContexto;
import model.enums.StatusTarefa;
import model.exceptions.UsuarioException;
import java.util.Scanner;

public abstract class Usuario {
    protected String nome;
    protected String email;
    protected String senha;

    // CONSTRUTOR
    public Usuario(String nome, String email, String senha) {
        if(!validarNome(nome).matches("^[A-ZÀ-Ý][a-zà-ÿ]+( [A-ZÀ-Ý][a-zà-ÿ]+)*$")) {
            throw new UsuarioException("Nome invalido");
        }
        if(!validarEmail(email)) {
            throw new UsuarioException("Email invalido!");
        }
        if(senha == null || senha.isBlank() || senha.length() != 6) {
            throw new UsuarioException("Senha invalida!");
        }

        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    // GETTERS e SETTERS
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public String getSenha() {return senha;}

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // VALIDAÇÕES DOS ATRIBUTOS
    protected String validarNome(String nome) {
        if(nome == null || nome.isBlank()) return nome;

        String [] partesNome = nome.trim().replaceAll("\\s+"," ").split(" ");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < partesNome.length; i++) {
            String parte = partesNome[i];
            sb.append(parte.substring(0, 1).toUpperCase()).append(parte.substring(1).toLowerCase());

            if (i < partesNome.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
    protected boolean validarEmail(String email) {
        return email != null && !email.isBlank() && email.matches("^[^@]+@[^@]+\\\\.[^@]+$");
    }

    // PUBLICOS ABSTRATOS
    public abstract boolean podeCriarProjeto();
    public abstract boolean podeCriarTarefa();
    public abstract boolean podeReceberTarefa();
    public abstract boolean podeAlterarPrioridadeDiretaProjeto();
    public abstract boolean podeAlterarPrioridadeDiretaTarefa();
    public abstract boolean podeAlterarStatusDiretamenteTarefa();
    public abstract boolean podeAlterarStatusTarefa(StatusTarefa statusTarefa);
    public abstract boolean podeSolicitarAlteracaoPrioridadeProjeto();
    public abstract boolean podeSolicionarAlteracaoStatusTarefa();
    public abstract boolean podeAprovarAlteracaoPrioridadeProjeto();
    public abstract boolean podeAprovarAlteracaoStatusTarefa();
    public abstract void exibirMenu(Scanner sc, AplicacaoContexto ctx);

    public String resumo() {
        return nome + ";" + getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", tipo=" + getClass().getSimpleName() +
                '}';
    }

    public String toFileString() {
        return nome + ";" +
                email + ";" +
                senha + ";" +
                getClass().getSimpleName();
    }

    public static Usuario fromFileString(String linha) {
        String[] dados = linha.split(";");

        String nome = dados[0];
        String email = dados[1];
        String senha = dados[2];
        String tipo = dados[3];

        switch (tipo) {
            case "Gerente":
                return new Gerente(nome, email, senha);
            case "Coordenador":
                return new Coordenador(nome, email, senha);
            case "Desenvolvedor":
                return new Desenvolvedor(nome, email, senha);
            default:
                throw new UsuarioException("Tipo de usuário inválido" + tipo);
        }
    }
}