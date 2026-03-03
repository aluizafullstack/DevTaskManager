package model.entities;

import model.enums.PrioridadeTarefa;
import model.enums.StatusTarefa;
import model.exceptions.TarefaException;
import model.exceptions.UsuarioException;

import java.util.List;

public class Tarefa {
    private Projeto projeto;
    private String nomeTarefa;
    private String descricaoTarefa;
    private StatusTarefa statusTarefa;
    private PrioridadeTarefa prioridadeTarefa;
    private Usuario responsavelTarefa;

    // CONSTRUTOR
    public Tarefa(Projeto projeto, String nomeTarefa, String descricaoTarefa, Usuario responsavelTarefa) {
        this.projeto = projeto;
        this.nomeTarefa = nomeTarefa;
        this.descricaoTarefa = descricaoTarefa;
        this.responsavelTarefa = responsavelTarefa;
        this.statusTarefa = StatusTarefa.ABERTA;
        this.prioridadeTarefa = PrioridadeTarefa.BAIXA;
    }

    // GETTERS e SETTERS

    public Projeto getProjeto() {
        return projeto;
    }
    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public String getDescricaoTarefa() {
        return descricaoTarefa;
    }

    public StatusTarefa getStatusTarefa() {
        return statusTarefa;
    }

    public PrioridadeTarefa getPrioridadeTarefa() {
        return prioridadeTarefa;
    }

    public Usuario getResponsavelTarefa() {
        return responsavelTarefa;
    }

    public void setPrioridadeTarefa(PrioridadeTarefa prioridadeTarefa) {
        this.prioridadeTarefa = prioridadeTarefa;
    }

    public void setStatusTarefa(StatusTarefa statusTarefa) {
        this.statusTarefa = statusTarefa;
    }

    // ================================
    // EXIBIÇÃO / SERIALIZAÇÃO
    // ================================
    public String exibirResumoTarefa() {
        return String.format(
                "Tarefa: %s | Status: %s | Prioridade: %s | Responsável: %s",
                nomeTarefa,
                statusTarefa,
                prioridadeTarefa,
                responsavelTarefa.getNome()
        );
    }
    public String exibirDetalhesTarefa() {
        return """
        ===============================
        TAREFA
        -------------------------------
        Nome do projeto: %s
        Nome da tarefa: %s
        Descrição: %s
        Status: %s
        Prioridade: %s
        Responsável: %s
        Gerente responsavel: %s
        ===============================
        """.formatted(
                projeto.getNomeProjeto(),
                nomeTarefa,
                descricaoTarefa,
                statusTarefa,
                prioridadeTarefa,
                responsavelTarefa.getNome(),
                projeto.getGerenteResponsavel()
        );
    }
    @Override
    public String toString() {
        return "Tarefa{" +
                "titulo='" + nomeTarefa + '\'' +
                ", status=" + statusTarefa +
                ", prioridade=" + prioridadeTarefa +
                '}';
    }

    // PARA O ARQUIVO
    public String toFileString() {
        return projeto.getNomeProjeto() + ";" +
                nomeTarefa + ";" +
                descricaoTarefa + ";" +
                statusTarefa + ";" +
                prioridadeTarefa + ";" +
                responsavelTarefa.getNome();
    }
    public static Tarefa fromFileString(String linha, List<Usuario> usuarios, List<Projeto> projetos) {
        String[] dados = linha.split(";");
        String nomeProjeto = dados[0];
        String nomeTarefa = dados[1];
        String descricaoTarefa = dados[2];
        StatusTarefa statusTarefa = StatusTarefa.valueOf(dados[3]);
        PrioridadeTarefa prioridadeTarefa = PrioridadeTarefa.valueOf(dados[4]);
        String responsavelTarefa = dados[5];

        Projeto projeto = null;
        for (Projeto p : projetos) {
            if (p.getNomeProjeto().equals(nomeProjeto)) {
                projeto = p;
                break;
            }
        }

        if (projeto == null) {
            throw new TarefaException("Projeto não encontrado ao carregar tarefa");
        }

        Usuario responsavel = null;
        for(Usuario usuario : usuarios) {
            if (usuario.getNome().equals(responsavelTarefa)) {
                responsavel = usuario;
            }
        }

        if(responsavel == null) {
            throw new UsuarioException("Responsável da tarefa não encontrado");
        }

        Tarefa tarefa = new Tarefa(projeto, nomeTarefa, descricaoTarefa, responsavel);
        tarefa.setStatusTarefa(statusTarefa);
        tarefa.setPrioridadeTarefa(prioridadeTarefa);
        return tarefa;
    }
}