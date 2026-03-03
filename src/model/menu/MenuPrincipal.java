package model.menu;

import model.contexto.AplicacaoContexto;
import model.entities.Usuario;
import model.services.*;
import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner sc;
    private final Usuario usuario;
    private final AplicacaoContexto ctx;

    public MenuPrincipal(Scanner sc,  Usuario usuario, AplicacaoContexto ctx) {
        this.sc = sc;
        this.usuario = usuario;
        this.ctx = ctx;
    }

    public void exibir() {
        usuario.exibirMenu(sc, ctx);
    }
}
