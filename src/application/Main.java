package application;

import model.contexto.AplicacaoContexto;
import model.menu.MenuLogin;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AplicacaoContexto ctx = new AplicacaoContexto();
        MenuLogin menuLogin = new MenuLogin(ctx, sc);
        menuLogin.exibir();

        sc.close();
    }
}
