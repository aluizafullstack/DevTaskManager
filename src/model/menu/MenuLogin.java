package model.menu;

import model.contexto.AplicacaoContexto;
import model.entities.Usuario;
import model.enums.TipoUsuario;
import model.exceptions.UsuarioException;
import model.services.*;

import java.awt.*;
import java.util.Scanner;

public class MenuLogin {
    private final AplicacaoContexto ctx;
    private final Scanner sc;

    public MenuLogin(AplicacaoContexto ctx, Scanner sc) {
        this. ctx = ctx;
        this.sc = sc;
    }
    public void exibir() {

        while (true) {

            System.out.println("""
                ======================
                1 - Login
                2 - Alterar a senha
                3 - Criar usuário
                0 - Sair
                ======================
                """);
            int opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1 -> {
                    try {
                        login();
                        limparConsole();
                    } catch (UsuarioException e) {
                        System.out.println("Erro: " + e.getMessage());
                        aguardar();
                        limparConsole();
                    }
                }
                case 2 -> {
                    alterarSenha();
                    limparConsole();
                }
                case 3 -> {
                    criarUsuario();
                    limparConsole();
                }
                case 0 -> {
                    System.out.println("Encerrando...");return; }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
    private void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Usuario usuario = ctx.getUsuarioService().login(email, senha);
        System.out.println("Login realizado com sucesso!");
        new MenuPrincipal(sc, usuario, ctx).exibir();
    }
    private void alterarSenha() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha atual: ");
        String senhaAtual = sc.nextLine();

        System.out.print("Nova senha: ");
        String novaSenha = sc.nextLine();

        ctx.getUsuarioService().alterarSenha(email, senhaAtual, novaSenha);
        System.out.println("Senha alterada com sucesso");
    }
    private void criarUsuario() {
        System.out.println("====NOVO USUÁRIO====");
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();


        TipoUsuario[] tipoUsuarios = TipoUsuario.values();
        System.out.println("====TIPOS DE USUÁRIOS===");
        for ( int i = 0; i < tipoUsuarios.length; i++ ) {
            System.out.println((i + 1) + " - " + tipoUsuarios[i].toString());
        }
        System.out.println("Escolha o tipo de usuário que você deseja ser: ");
        int opcaoUsuario = sc.nextInt();
        sc.nextLine();
        if (opcaoUsuario < 0 || opcaoUsuario > tipoUsuarios.length) {
            System.out.println("Opção inválida!");
            return;
        }
        TipoUsuario tipoUsuario = tipoUsuarios[opcaoUsuario - 1];
        String tipoUsuarioSelecionado = String.valueOf(tipoUsuario);

        ctx.getUsuarioService().criarUsuario(nome, email, senha, tipoUsuarioSelecionado);
        System.out.println("Usuario criado com sucesso!");
    }
    private void limparConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    private void aguardar() {
        try {
            Thread.sleep(950);
        } catch (InterruptedException ignored) {
        }
    }
}
