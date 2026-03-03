package model.entities;

import model.enums.PrioridadeProjeto;
import model.exceptions.UsuarioException;

import java.util.ArrayList;
import java.util.List;

public class Projeto {
    private String nomeProjeto;
    private Usuario gerenteResponsavel;
    private PrioridadeProjeto prioridadeProjeto;
    private List<Tarefa> tarefas = new ArrayList<>();

    // CONSTRUTOR
    public Projeto(String nomeProjeto, Usuario gerenteResponsavel) {
        this.nomeProjeto = nomeProjeto;
        this.gerenteResponsavel = gerenteResponsavel;
        this.prioridadeProjeto = PrioridadeProjeto.BAIXA;
    }

    // GETTERS e SETTERS
    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public Usuario getGerenteResponsavel() {
        return gerenteResponsavel;
    }

    public PrioridadeProjeto getPrioridadeProjeto() {
        return prioridadeProjeto;
    }

    public void setPrioridadeProjeto(PrioridadeProjeto prioridadeProjeto) {
        this.prioridadeProjeto = prioridadeProjeto;
    }

    // PUBLICO
    public List<Tarefa> listarTarefas() {
        return new ArrayList<>(tarefas);
    }

    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
    }
    public void removerTarefa(Tarefa tarefa) {
        tarefas.remove(tarefa);
    }

    public String exibirResumoProjeto() {
        return String.format(
                "Projeto: %s | Gerente responsável: %s | Prioridade: %s",
                nomeProjeto,
                gerenteResponsavel.getNome(),
                prioridadeProjeto
        );
    }

    public String exibirDetalhesProjeto() {
        StringBuilder sb = new StringBuilder();

        sb.append("""
        ===============================
        PROJETO
        -------------------------------
        Nome: %s
        Prioridade: %s
        Gerente: %s
        -------------------------------
        Tarefas:
        """.formatted(
                nomeProjeto,
                prioridadeProjeto,
                gerenteResponsavel.getNome()
        ));

        if (tarefas.isEmpty()) {
            sb.append("Nenhuma tarefa cadastrada\n");
        } else {
            for (Tarefa t : tarefas) {
                sb.append("- ").append(t.toFileString()).append("\n");
            }
        }

        sb.append("===============================\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "titulo='" + nomeProjeto + '\'' +
                ", prioridade=" + prioridadeProjeto +
                '}';
    }

    public String toFileString() {
        return nomeProjeto + ";" +
                prioridadeProjeto + ";" +
                gerenteResponsavel.getNome();
    }
    public static Projeto fromFileString(String linha, List<Usuario> usuarios) {
        String[] dados = linha.split(";");

        String nomeProjeto = dados[0];
        PrioridadeProjeto prioridadeProjeto =  PrioridadeProjeto.valueOf(dados[1]);
        String nomeGerente = dados[2];


        Usuario gerente = null;
        for (Usuario usuario: usuarios) {
            if(usuario.getNome().equals(nomeGerente)) {
                gerente = usuario;
                break;
            }
        }

        if(gerente == null) {
            throw new UsuarioException("Responsável da tarefa não encontrado");
        }

        Projeto projeto = new Projeto(nomeProjeto, gerente);
        projeto.setPrioridadeProjeto(prioridadeProjeto);

        return projeto;
    }
}