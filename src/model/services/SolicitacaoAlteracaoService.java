package model.services;

import model.entities.*;
import model.enums.PrioridadeProjeto;
import model.enums.StatusSolicitacao;
import model.enums.StatusTarefa;
import model.enums.TipoAlteracao;
import model.exceptions.PermissaoException;
import model.exceptions.SolicitacaoException;

import java.util.ArrayList;
import java.util.List;

public class SolicitacaoAlteracaoService {
    private final ProjetoService projetoService;
    private final TarefaService tarefaService;
    private final List<SolicitacaoAlteracao> solicitacaoAlteracoes;
    private final FileService fileService;

    public SolicitacaoAlteracaoService(FileService fileService,ProjetoService projetoService, TarefaService tarefaService, List<SolicitacaoAlteracao> solicitacaoAlteracoes) {
        this.fileService = fileService;
        this.projetoService = projetoService;
        this.tarefaService = tarefaService;
        this.solicitacaoAlteracoes = solicitacaoAlteracoes;
    }
    public SolicitacaoAlteracao solicitarAlteracaoPrioridadeProjeto(Usuario usuario, Projeto projeto, String novaPrioridadeProjeto, Comentario comentario) {
        if (!usuario.podeSolicitarAlteracaoPrioridadeProjeto()) {
            throw new SolicitacaoException("Usuario não pode solicitar alteração de prioridade do projeto");
        }
        SolicitacaoAlteracao solicitacaoAlteracao = new SolicitacaoAlteracao(usuario, projeto, novaPrioridadeProjeto, comentario);
        solicitacaoAlteracoes.add(solicitacaoAlteracao);
        fileService.salvarSolicitacoes(solicitacaoAlteracao);
        return solicitacaoAlteracao;
    }

    public SolicitacaoAlteracao solicitarAlteracaoStatusTarefa(Usuario usuario, Tarefa tarefa, String novaStatusTarefa, Comentario comentario) {
        if(!usuario.podeSolicionarAlteracaoStatusTarefa()) {
            throw new SolicitacaoException("Usuario não tem permissão para solicitar alteração de status");
        }
        SolicitacaoAlteracao solicitacaoAlteracao = new SolicitacaoAlteracao(usuario, tarefa, novaStatusTarefa, comentario);
        solicitacaoAlteracoes.add(solicitacaoAlteracao);
        fileService.salvarSolicitacoes(solicitacaoAlteracao);
        return solicitacaoAlteracao;
    }

    public void aprovarSolitacao(SolicitacaoAlteracao solicitacaoAlteracao, Usuario usuario,Comentario comentario) {
        validarPermissao(solicitacaoAlteracao, usuario);
        solicitacaoAlteracao.aprovar(comentario);
        aplicarAlteracao(solicitacaoAlteracao);
        fileService.reescreverSolitacoes(solicitacaoAlteracoes);
    }

    public void rejeitarSolitacao(SolicitacaoAlteracao solicitacaoAlteracao, Usuario usuario,Comentario comentario) {
        validarPermissao(solicitacaoAlteracao, usuario);
        solicitacaoAlteracao.rejeitar(comentario);
        fileService.reescreverSolitacoes(solicitacaoAlteracoes);
    }

    public void alterarSolitacao(SolicitacaoAlteracao solicitacaoAlteracao, Usuario usuario,Comentario comentario) {
        validarPermissao(solicitacaoAlteracao, usuario);
        solicitacaoAlteracao.alterar(comentario);
        fileService.reescreverSolitacoes(solicitacaoAlteracoes);
    }
    public void atualizarSolitacao(SolicitacaoAlteracao solicitacaoAlteracao, Usuario usuario,Comentario comentario) {
        validarPermissao(solicitacaoAlteracao, usuario);
        solicitacaoAlteracao.alterar(comentario);
        fileService.reescreverSolitacoes(solicitacaoAlteracoes);
    }
    public String exibirSolicitacoes(List<SolicitacaoAlteracao> lista) {
        StringBuilder sb = new StringBuilder();

        for (SolicitacaoAlteracao s : lista) {
            sb.append("================================\n");
            sb.append(s.exibirDetalhesSolicitacao()).append("\n");

            if (!s.getComentarios().isEmpty()) {
                sb.append("Comentários:\n");
                sb.append(s.exibirResumoComentario());
            }
        }
        return sb.toString();
    }
    public List<SolicitacaoAlteracao> listarSolicitacoesPendentes() {
        List<SolicitacaoAlteracao> pendentes = new ArrayList<>();

        for (SolicitacaoAlteracao s : solicitacaoAlteracoes) {
            if (s.getStatusSolicitacao() == StatusSolicitacao.PENDENTE) {
                pendentes.add(s);
            }
        }
        return pendentes;
    }

    public List<SolicitacaoAlteracao> ListatSolicitacaoPendenteTipo(TipoAlteracao tipoAlteracao) {
        List<SolicitacaoAlteracao> pendentes = new ArrayList<>();

        for (SolicitacaoAlteracao s : solicitacaoAlteracoes) {
            if (s.getStatusSolicitacao() == StatusSolicitacao.PENDENTE) {
                if(s.getTipoAlteracao() == tipoAlteracao) {
                    pendentes.add(s);
                }
            }
        }
        return pendentes;
    }

    public List<SolicitacaoAlteracao> listarTodas() {
        return new ArrayList<>(solicitacaoAlteracoes);
    }

    // PRIVADOS
    private void validarPermissao(SolicitacaoAlteracao solicitacaoAlteracao, Usuario usuario) {
        switch (solicitacaoAlteracao.getTipoAlteracao()) {
            case PRIORIDADE_PROJETO:
                if(!usuario.podeAprovarAlteracaoPrioridadeProjeto()) {
                    throw new PermissaoException("Usuario não tem permissão para aprovar solicitação de alteração de prioridade do projeto");
                }
                break;

            case STATUS_TAREFA:
                if(!usuario.podeAprovarAlteracaoStatusTarefa()) {
                    throw new PermissaoException("Usuario não tem permissão para aprovar solicitação de alteração de status da tarefa");
                }
                break;
        }
    }
    private void aplicarAlteracao(SolicitacaoAlteracao solicitacaoAlteracao) {
        if (solicitacaoAlteracao.getTipoAlteracao() == TipoAlteracao.PRIORIDADE_PROJETO) {
            projetoService.alterarPrioridadeDiretaProjeto(solicitacaoAlteracao.getProjetoAlvo(), solicitacaoAlteracao.getSolicitante(), PrioridadeProjeto.valueOf(solicitacaoAlteracao.getAlteracaoPrioridadeProjeto()));

        } else if (solicitacaoAlteracao.getTipoAlteracao() == TipoAlteracao.STATUS_TAREFA) {
            tarefaService.alterarStatusTarefa(solicitacaoAlteracao.getTarefaAlvo(), StatusTarefa.valueOf(solicitacaoAlteracao.getAlteracaoStatusTarefa()), solicitacaoAlteracao.getSolicitante());
        }
    }
}