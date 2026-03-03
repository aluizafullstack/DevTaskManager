package model.entities;

import model.contexto.AplicacaoContexto;
import model.enums.StatusTarefa;
import model.menu.MenuDesenvolvedor;

import java.util.Scanner;

public class Desenvolvedor extends Usuario {

    public Desenvolvedor(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public boolean podeCriarProjeto() {

        return false;
    }

    @Override
    public boolean podeCriarTarefa() {

        return false;
    }

    @Override
    public boolean podeReceberTarefa() {
        return true;
    }

    @Override
    public boolean podeAlterarPrioridadeDiretaProjeto() {

        return false;
    }

    @Override
    public boolean podeAlterarPrioridadeDiretaTarefa() {

        return false;
    }

    @Override
    public boolean podeAlterarStatusTarefa(StatusTarefa statusTarefa) {

        return false;
    }

    @Override
    public boolean podeSolicitarAlteracaoPrioridadeProjeto() {

        return false;
    }

    @Override
    public boolean podeAprovarAlteracaoPrioridadeProjeto() {

        return false;
    }

    @Override
    public boolean podeSolicionarAlteracaoStatusTarefa() {

        return true;
    }

    @Override
    public boolean podeAprovarAlteracaoStatusTarefa() {

        return false;
    }

    @Override
    public boolean podeAlterarStatusDiretamenteTarefa() {

        return false;
    }

    @Override
    public void exibirMenu(Scanner sc, AplicacaoContexto ctx) {
        new MenuDesenvolvedor(sc, this, ctx.getTarefaService(), ctx.getSolicitacaoAlteracaoService(), ctx.getComentarioService()).exibir();
    }
}