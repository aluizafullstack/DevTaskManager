package model.contexto;

import model.entities.*;
import model.services.*;

import java.util.ArrayList;
import java.util.List;


public class AplicacaoContexto {
    private final FileService fileService;

    // LISTAS (estado da aplicação)
    private final List<Usuario> usuarios;
    private final List<Projeto> projetos;
    private final List<Tarefa> tarefas;
    private final List<SolicitacaoAlteracao> solicitacaoAlteracao;
    private final List<Comentario> comentarios;

    // SERVICES
    private final UsuarioService usuarioService;
    private final ProjetoService projetoService;
    private final TarefaService tarefaService;
    private final ComentarioService comentarioService;
    private final SolicitacaoAlteracaoService solicitacaoAlteracaoService;

    public AplicacaoContexto() {

        this.fileService = new FileService();

        // carregar dados
        List<Usuario> usuarioCarregado = fileService.carregarUsuarios();
        this.usuarios = (usuarioCarregado != null) ? usuarioCarregado : new ArrayList<>();

        List<Projeto> projetoCarregado = fileService.carregarProjetos(usuarios);
        this.projetos = (projetoCarregado != null) ? projetoCarregado : new ArrayList<>();

        List<Tarefa> tarefaCarregado = fileService.carregarTarefas(usuarios, projetos);
        this.tarefas = (tarefaCarregado!= null) ? tarefaCarregado : new ArrayList<>();

        List<Comentario> comentarioCarregado = fileService.carregarComentarios(usuarios);
        this.comentarios = (comentarioCarregado != null) ? comentarioCarregado : new ArrayList<>();

        List<SolicitacaoAlteracao> solicitacaoAlteracaoCarregado = fileService.carregarSolicitacoes(usuarios, projetos, tarefas, comentarios);
        this.solicitacaoAlteracao = (solicitacaoAlteracaoCarregado != null) ? solicitacaoAlteracaoCarregado : new ArrayList<>();

        // criar services passando as listas
        this.usuarioService = new UsuarioService(fileService, usuarios);
        this.projetoService = new ProjetoService(fileService, projetos);
        this.tarefaService = new TarefaService(fileService, tarefas);
        this.comentarioService = new ComentarioService(fileService, comentarios);
        this.solicitacaoAlteracaoService = new SolicitacaoAlteracaoService(fileService, projetoService, tarefaService, solicitacaoAlteracao);
    }

    // GETTERS
    public UsuarioService getUsuarioService() {
        return usuarioService;
    }
    public ProjetoService getProjetoService() {
        return projetoService;
    }
    public TarefaService getTarefaService() {
        return tarefaService;
    }
    public ComentarioService getComentarioService() {
        return comentarioService;
    }
    public SolicitacaoAlteracaoService getSolicitacaoAlteracaoService() {
        return solicitacaoAlteracaoService;
    }
}
