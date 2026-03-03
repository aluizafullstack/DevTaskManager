package model.entities;

import model.contexto.AplicacaoContexto;
import model.enums.StatusTarefa;
import model.menu.MenuCoordenador;

import java.util.Scanner;

public class Coordenador extends Usuario{

    public Coordenador(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public boolean podeCriarProjeto() {
        return false;
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
        return false;
    }

    @Override
    public boolean podeAlterarPrioridadeDiretaTarefa() {
        return true;
    }

    @Override
    public boolean podeAlterarStatusTarefa(StatusTarefa statusTarefa) {
        return statusTarefa == StatusTarefa.EM_ANDAMENTO || statusTarefa == StatusTarefa.CONCLUIDA;
    }

    @Override
    public boolean podeSolicitarAlteracaoPrioridadeProjeto() {
        return true;
    }

    @Override
    public boolean podeAprovarAlteracaoPrioridadeProjeto() {
        return false;
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
        return false;
    }

    @Override
    public void exibirMenu(Scanner sc, AplicacaoContexto ctx) {
        new MenuCoordenador(sc, this, ctx.getProjetoService(), ctx.getSolicitacaoAlteracaoService(), ctx.getComentarioService(), ctx.getTarefaService(), ctx.getUsuarioService()).exibir();
    }
}