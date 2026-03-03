package model.entities;

import model.contexto.AplicacaoContexto;
import model.enums.StatusTarefa;
import model.menu.MenuGerente;
import java.util.Scanner;

public class Gerente extends Usuario{

    public Gerente(String nome, String email, String senha) {

        super(nome, email, senha);
    }

    @Override
    public boolean podeCriarProjeto() {

        return true;
    }

    @Override
    public boolean podeCriarTarefa() {

        return true;
    }

    @Override
    public boolean podeReceberTarefa() {
        return false;
    }

    @Override
    public boolean podeAlterarPrioridadeDiretaProjeto() {

        return true;
    }

    @Override
    public boolean podeAlterarPrioridadeDiretaTarefa() {

        return true;
    }

    @Override
    public boolean podeAlterarStatusTarefa(StatusTarefa statusTarefa) {

        return true;
    }

    @Override
    public boolean podeSolicitarAlteracaoPrioridadeProjeto() {

        return false;
    }

    @Override
    public boolean podeAprovarAlteracaoPrioridadeProjeto() {

        return true;
    }

    @Override
    public boolean podeSolicionarAlteracaoStatusTarefa() {

        return false;
    }

    @Override
    public boolean podeAprovarAlteracaoStatusTarefa() {

        return true;
    }

    @Override
    public boolean podeAlterarStatusDiretamenteTarefa() {

        return true;
    }

    @Override
    public void exibirMenu(Scanner sc, AplicacaoContexto ctx) {
        new MenuGerente(ctx.getProjetoService(), ctx.getTarefaService(), ctx.getSolicitacaoAlteracaoService(), ctx.getComentarioService(), sc, this, ctx.getUsuarioService()).exibir();

    }
}