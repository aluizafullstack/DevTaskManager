package model.entities;

import model.enums.StatusSolicitacao;
import model.enums.TipoAlteracao;
import model.exceptions.SolicitacaoException;
import model.exceptions.UsuarioException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SolicitacaoAlteracao {
    private String idSolicitacao;
    private Usuario solicitante;
    private Projeto projetoAlvo;
    private String alteracaoPrioridadeProjeto;
    private String alteracaoStatusTarefa;
    private TipoAlteracao tipoAlteracao;
    private StatusSolicitacao statusSolicitacao;
    private String dataSolicitacao;
    private Tarefa tarefaAlvo;
    private List<Comentario> comentarios = new ArrayList<>();

    // CONSTRUTORES
    public  SolicitacaoAlteracao(Usuario solicitante, Projeto projetoAlvo, String alteracaoPrioridadeProjeto, Comentario comentario) {
        this.idSolicitacao = UUID.randomUUID().toString().substring(0, 8);
        this.dataSolicitacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.solicitante = solicitante;
        this.projetoAlvo = projetoAlvo;
        this.alteracaoPrioridadeProjeto = alteracaoPrioridadeProjeto;
        this.tipoAlteracao = TipoAlteracao.PRIORIDADE_PROJETO;
        this.statusSolicitacao = StatusSolicitacao.PENDENTE;
        this.comentarios.add(comentario);
    }

    public SolicitacaoAlteracao(Usuario solicitante, Tarefa tarefaAlvo, String alteracaoStatusTarefa, Comentario comentario) {
        this.idSolicitacao = UUID.randomUUID().toString().substring(0, 8);
        this.dataSolicitacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.solicitante = solicitante;
        this.tarefaAlvo = tarefaAlvo;
        this.alteracaoStatusTarefa = alteracaoStatusTarefa;
        this.tipoAlteracao = TipoAlteracao.STATUS_TAREFA;
        this.statusSolicitacao = StatusSolicitacao.PENDENTE;
        this.comentarios.add(comentario);
    }

    protected SolicitacaoAlteracao(String idSolicitacao, String dataSolicitacao, Usuario solicitante, Projeto projetoAlvo, String alteracaoPrioridadeProjeto, StatusSolicitacao statusSolicitacao, List<Comentario> comentarios) {
        this.idSolicitacao = idSolicitacao;
        this.dataSolicitacao = dataSolicitacao;
        this.solicitante = solicitante;
        this.projetoAlvo = projetoAlvo;
        this.alteracaoPrioridadeProjeto = alteracaoPrioridadeProjeto;
        this.tipoAlteracao = TipoAlteracao.PRIORIDADE_PROJETO;
        this.statusSolicitacao = statusSolicitacao;
        this.comentarios = comentarios;
    }

    protected SolicitacaoAlteracao(String idSolicitacao, String dataSolicitacao, Usuario solicitante, Tarefa tarefaAlvo, String alteracaoStatusTarefa, StatusSolicitacao statusSolicitacao, List<Comentario> comentarios) {
        this.idSolicitacao = idSolicitacao;
        this.dataSolicitacao = dataSolicitacao;
        this.solicitante = solicitante;
        this.tarefaAlvo = tarefaAlvo;
        this.alteracaoStatusTarefa = alteracaoStatusTarefa;
        this.tipoAlteracao = TipoAlteracao.STATUS_TAREFA;
        this.statusSolicitacao = statusSolicitacao;
        this.comentarios = comentarios;
    }

    // GETTERS
    public String getIdSolicitacao() {
        return idSolicitacao;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public Projeto getProjetoAlvo() {
        return projetoAlvo;
    }

    public String getAlteracaoPrioridadeProjeto() {
        return alteracaoPrioridadeProjeto;
    }

    public String getAlteracaoStatusTarefa() {
        return alteracaoStatusTarefa;
    }

    public TipoAlteracao getTipoAlteracao() {
        return tipoAlteracao;
    }

    public StatusSolicitacao getStatusSolicitacao() {
        return statusSolicitacao;
    }

    public String getDataSolicitacao() {
        return dataSolicitacao;
    }

    public Tarefa getTarefaAlvo() {
        return tarefaAlvo;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    // SETTERS
    public void setStatusSolicitacao(StatusSolicitacao statusSolicitacao) {
        this.statusSolicitacao = statusSolicitacao;
    }

    // PUBLICOS
    public List<Comentario> listarComentarios() {
        return new ArrayList<>(comentarios);
    }

    public void aprovar(Comentario comentario) {
        if(!estaPendente()) {
            throw new SolicitacaoException("Solicitação não está pendente");
        }
        this.statusSolicitacao = StatusSolicitacao.APROVADA;
        adicionarComentario(comentario);
    }

    public void rejeitar(Comentario comentario) {
        if(!estaPendente()) {
            throw new SolicitacaoException("Solicitação está pendente");
        }
        this.statusSolicitacao = StatusSolicitacao.REJEITADA;
        adicionarComentario(comentario);
    }

    public void alterar(Comentario comentario) {
        if(!estaPendente()) {
            throw new SolicitacaoException("Solicitação está pendente");
        }
        this.statusSolicitacao = StatusSolicitacao.PENDENTE;
        adicionarComentario(comentario);
    }

    public boolean estaPendente() {
        return statusSolicitacao == StatusSolicitacao.PENDENTE;
    }

    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    private static List<Comentario> filtrarComentariosDaSolicitacao(
            String idSolicitacao,
            List<Comentario> comentarios
    ) {
        List<Comentario> lista = new ArrayList<>();
        for (Comentario c : comentarios) {
            if (c.getIdSolicitacao().equals(idSolicitacao)) {
                lista.add(c);
            }
        }
        return lista;
    }

    public String exibirResumoComentario() {
        return String.format(
                "Solicitação: %s | Data/horas: %s | Status: %s | Tipo de ação: %s",
                idSolicitacao,
                dataSolicitacao,
                statusSolicitacao,
                tipoAlteracao
        );
    }
    public String exibirDetalhesSolicitacao() {
        StringBuilder sb = new StringBuilder();

        sb.append("""
        ===============================
        SOLICITAÇÃO DE ALTERAÇÃO
        -------------------------------
        ID: %s
        Tipo: %s
        Status: %s
        Solicitante: %s
        Data: %s
        """.formatted(
                idSolicitacao,
                tipoAlteracao,
                statusSolicitacao,
                solicitante.getNome(),
                dataSolicitacao
        ));

        if (projetoAlvo != null) {
            sb.append("Projeto: ")
                    .append(projetoAlvo.getNomeProjeto())
                    .append("\nNovo valor: ")
                    .append(alteracaoPrioridadeProjeto)
                    .append("\n");
        }

        if (tarefaAlvo != null) {
            sb.append("Tarefa: ")
                    .append(tarefaAlvo.getNomeTarefa())
                    .append("\nNovo valor: ")
                    .append(alteracaoStatusTarefa)
                    .append("\n");
        }

        sb.append("-------------------------------\nComentários:\n");

        if (comentarios.isEmpty()) {
            sb.append("Nenhum comentário\n");
        } else {
            for (Comentario c : comentarios) {
                sb.append("- ")
                        .append(c.toFileString())
                        .append("\n");
            }
        }

        sb.append("===============================\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Solicitação{" +
                "ID='" + idSolicitacao + '\'' +
                "Data/Hora=" + dataSolicitacao +
                "Status=" + statusSolicitacao +
                "Tipo=" + tipoAlteracao +
                '}';
    }

    public String toFileString() {
        return idSolicitacao + ";" +
                tipoAlteracao + ";" +
                statusSolicitacao + ";" +
                dataSolicitacao + ";" +
                solicitante.getNome() + ";" +
                (projetoAlvo != null ? projetoAlvo.getNomeProjeto() : "null") + ";" +
                (tarefaAlvo != null ? tarefaAlvo.getNomeTarefa() : "null") + ";" +
                (alteracaoPrioridadeProjeto != null ? alteracaoPrioridadeProjeto : "null") + ";" +
                (alteracaoStatusTarefa != null ? alteracaoStatusTarefa : "null");
    }

    public static SolicitacaoAlteracao fromFileString(
            String linha,
            List<Usuario> usuarios,
            List<Projeto> projetos,
            List<Tarefa> tarefas,
            List<Comentario> comentarios
    ) {
        String[] dados = linha.split(";");

        String id = dados[0];
        TipoAlteracao tipo = TipoAlteracao.valueOf(dados[1]);
        StatusSolicitacao status = StatusSolicitacao.valueOf(dados[2]);
        String data = dados[3];
        String nomeSolicitante = dados[4];
        String nomeProjeto = dados[5];
        String nomeTarefa = dados[6];
        String novaPrioridade = dados[7];
        String novoStatus = dados[8];

        // buscar solicitante
        Usuario solicitante = null;
        for (Usuario u : usuarios) {
            if (u.getNome().equals(nomeSolicitante)) {
                solicitante = u;
                break;
            }
        }
        if (solicitante == null) {
            throw new UsuarioException("Solicitante não encontrado");
        }

        // filtrar comentários da solicitação
        List<Comentario> comentariosDaSolicitacao =
                filtrarComentariosDaSolicitacao(id, comentarios);

        // reconstrução correta
        if (tipo == TipoAlteracao.PRIORIDADE_PROJETO) {

            Projeto projeto = null;
            for (Projeto p : projetos) {
                if (p.getNomeProjeto().equals(nomeProjeto)) {
                    projeto = p;
                    break;
                }
            }

            if (projeto == null) {
                throw new SolicitacaoException("Projeto não encontrado");
            }

            return new SolicitacaoAlteracao(
                    id,
                    data,
                    solicitante,
                    projeto,
                    novaPrioridade,
                    status,
                    comentariosDaSolicitacao
            );

        } else {

            Tarefa tarefa = null;
            for (Tarefa t : tarefas) {
                if (t.getNomeTarefa().equals(nomeTarefa)) {
                    tarefa = t;
                    break;
                }
            }

            if (tarefa == null) {
                throw new SolicitacaoException("Tarefa não encontrada");
            }

            return new SolicitacaoAlteracao(
                    id,
                    data,
                    solicitante,
                    tarefa,
                    novoStatus,
                    status,
                    comentariosDaSolicitacao
            );
        }
    }

}