package model.services;

import model.entities.Projeto;
import model.entities.Tarefa;
import model.entities.Usuario;
import model.enums.PrioridadeTarefa;
import model.enums.StatusTarefa;
import model.exceptions.PermissaoException;
import model.exceptions.ProjetoException;
import model.exceptions.TarefaException;

import java.util.ArrayList;
import java.util.List;

public class TarefaService {
    private final FileService fileService;
    private final List<Tarefa> tarefas;


    public TarefaService(FileService fileService, List<Tarefa> tarefas) {
        this.fileService = fileService;
        this.tarefas = tarefas;
    }
    public Tarefa criarTarefa(Projeto projeto, String nomeTarefa, String descricao, Usuario responsavelTarefa, Usuario criadorTarefa) {


        if (projeto == null) {
            throw new ProjetoException("Projeto não foi encontrado.");
        }

        if(!criadorTarefa.podeCriarTarefa()) {
            throw new PermissaoException("Usuário não tem permissão para criar tarefas");
        }
        Tarefa tarefa = new Tarefa(projeto, nomeTarefa, descricao, responsavelTarefa);
        tarefas.add(tarefa);
        fileService.salvarTarefas(tarefa);
        return tarefa;
    }

    public void alterarStatusDiretamenteTarefa(Usuario usuario, StatusTarefa novoStatusTarefa, Tarefa tarefa) {
        if(!usuario.podeAlterarStatusDiretamenteTarefa()) {
            throw new PermissaoException("Usuario não tem permissão para solicitar alteração de prioridade");
        }

        if(novoStatusTarefa == tarefa.getStatusTarefa()) {
            throw new TarefaException("A tarefa já está com esse status");
        }

        if(tarefa.getStatusTarefa() == StatusTarefa.CANCELADA && novoStatusTarefa == StatusTarefa.CANCELADA) {
            throw new TarefaException("Não é possível alterar o Status de uma tarefa CANCELADA");
        }
        tarefa.setStatusTarefa(novoStatusTarefa);
        fileService.reescreverTarefas(tarefas);
    }

    public void alterarStatusTarefa(Tarefa tarefa, StatusTarefa novoStatusTarefa, Usuario usuario) {
        if(!usuario.podeAlterarStatusTarefa(novoStatusTarefa)) {
            throw new PermissaoException("Usuário não tem permissão para alterar status");
        }

        if (tarefa.getStatusTarefa() != StatusTarefa.CONCLUIDA && novoStatusTarefa == StatusTarefa.FINALIZADA) {
            throw new TarefaException("A tarefa só pode ser FINALIZADA se estiver CONCLUIDA");
        }

        if(novoStatusTarefa == tarefa.getStatusTarefa()) {
            throw new TarefaException("A tarefa já está com esse status");
        }

        if(novoStatusTarefa == StatusTarefa.FINALIZADA || novoStatusTarefa == StatusTarefa.CANCELADA) {
            throw new TarefaException("Não é possivel realizar essa alteração na tarefa");
        }
        tarefa.setStatusTarefa(novoStatusTarefa);
        fileService.reescreverTarefas(tarefas);
    }
    public void alterarPrioridadeDiretamenteTarefa(Usuario usuario, PrioridadeTarefa novoPrioridadeTarefa, Tarefa tarefa) {
        if(!usuario.podeAlterarPrioridadeDiretaTarefa()) {
            throw new PermissaoException("Usuario não tem permissão para solicitar alteração de prioridade");
        }

        if(novoPrioridadeTarefa == tarefa.getPrioridadeTarefa()) {
            throw new TarefaException("A tarefa já está com essa prioridade");
        }
        tarefa.setPrioridadeTarefa(novoPrioridadeTarefa);
        fileService.reescreverTarefas(tarefas);
    }

    public List<Tarefa> buscarTarefaporNomeUsuario(Usuario usuario) {
        List<Tarefa> tarefaUsuario = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getResponsavelTarefa().equals(usuario)) {
                tarefaUsuario.add(tarefa);
            }
        }
        return tarefaUsuario;
    }

    public List<Tarefa> listarTarefas() {
        return new ArrayList<>(tarefas);
    }
    public List<Tarefa> listarTarefaAndamento(Usuario  usuario) {
        List<Tarefa> tarefaAndamento = new ArrayList<>();
        for (Tarefa tarefaStatus : tarefas) {
            if (tarefaStatus.getStatusTarefa().equals(StatusTarefa.EM_ANDAMENTO) && tarefaStatus.getResponsavelTarefa().equals(usuario)) {
                tarefaAndamento.add(tarefaStatus);
            }
        }
        return tarefaAndamento;
    }
    public List<Tarefa> listarTarefaAberta(Usuario  usuario) {
        List<Tarefa> tarefaAndamento = new ArrayList<>();
        for (Tarefa tarefaStatus : tarefas) {
            if (tarefaStatus.getStatusTarefa().equals(StatusTarefa.ABERTA) && tarefaStatus.getResponsavelTarefa().equals(usuario)) {
                tarefaAndamento.add(tarefaStatus);
            }
        }
        return tarefaAndamento;
    }
    public List<Tarefa> listartarefasPorProjeto(Projeto projeto) {
        return projeto.listarTarefas();
    }
}