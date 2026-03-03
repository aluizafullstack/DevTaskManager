package model.menu;

import model.entities.*;
import model.enums.*;
import model.exceptions.SolicitacaoException;
import model.exceptions.ValidacaoException;
import model.services.*;

import java.util.List;
import java.util.Scanner;

public class MenuCoordenador {
    private final Scanner sc;
    private final Usuario usuario;
    private final ProjetoService projetoService;
    private final TarefaService tarefaService;
    private final SolicitacaoAlteracaoService solicitacaoAlteracaoService;
    private final ComentarioService comentarioService;
    private final UsuarioService usuarioService;

    public MenuCoordenador(Scanner sc, Usuario usuario, ProjetoService projetoService, SolicitacaoAlteracaoService solicitacaoAlteracaoService, ComentarioService comentarioService, TarefaService tarefaService, UsuarioService usuarioService) {
        this.sc = sc;
        this.usuario = usuario;
        this.projetoService = projetoService;
        this.solicitacaoAlteracaoService = solicitacaoAlteracaoService;
        this.comentarioService = comentarioService;
        this.tarefaService = tarefaService;
        this.usuarioService = usuarioService;

    }

    public void exibir() {
        while (true) {
            System.out.println("""
                    ===== MENU COORDENADOR =====
                    1 - Criar tarefa
                    2 - Alterar prioridade tarefa
                    3 - Alterar status terefa
                    4 - Solicitar alteração de prioridade projeto
                    5 - Listar todas as solicitações
                    6 - Listar solicitações pendentes
                    7 - Analisar solicitações
                    0 - Sair
                    """);
            int opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1 -> criarTarefa();
                case 2 -> alterarPrioridadeTarefa();
                case 3 -> alterarStatusTarefa();
                case 4 -> solicitarAlteracaoPrioridadeProjeto();
                case 5 -> listarSolicitacoes();
                case 6 -> analisarSolicitacoes();
                case 0 -> {return; }
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private void criarTarefa() {

        List<Projeto> projetos = projetoService.listaProjetos();
        if (projetos.isEmpty()) {
            System.out.println("Nenhum projeto encontrado!");
            return;
        }
        System.out.println("====LISTA DE PROJETOS====");
        for (int i = 0; i < projetos.size(); i++) {
            System.out.println((i +1) + " - " + projetos.get(i).getNomeProjeto());
        }
        System.out.println("Escolha o projeto: ");
        int opcaoProjeto = sc.nextInt();
        sc.nextLine();
        if (opcaoProjeto < 0 || opcaoProjeto > projetos.size()) {
            System.out.println("Opção inválida!");
            return;
        }
        Projeto projetoSelecionado = projetos.get(opcaoProjeto - 1);


        List<Usuario> responsavelTarefa = usuarioService.listarUsuarioPodemReceberTarefas();
        if (responsavelTarefa.isEmpty()) {
            System.out.println("Nenhum usuario encontrado!");
            return;
        }
        for (int i = 0; i < responsavelTarefa.size(); i++) {
            System.out.println((i + 1) + " - " + responsavelTarefa.get(i).getNome());
        }
        System.out.println("Escolha um responsável: ");
        int opcaoResponsavel = sc.nextInt();
        sc.nextLine();
        if (opcaoResponsavel < 0 || opcaoResponsavel > responsavelTarefa.size()) {
            System.out.println("Opção inválida!");
        }
        Usuario responsavel = responsavelTarefa.get(opcaoResponsavel - 1);


        System.out.println("Escreva o nome da tarefa: ");
        String nomeTarefa = sc.nextLine();
        System.out.println("Escreva a descrição da tarefa: ");
        String descricaoTarefa = sc.nextLine();

        tarefaService.criarTarefa(projetoSelecionado, nomeTarefa, descricaoTarefa, responsavel, usuario);
        System.out.println("Tarefa criada com sucesso!");
    }

    private void alterarPrioridadeTarefa() {

        List<Projeto> projetos = projetoService.listaProjetos();
        if (projetos.isEmpty()) {
            System.out.println("Nenhum projeto encontrado!");
            return;
        }
        System.out.println("====LISTA DE PROJETOS====");
        for (int i = 0; i < projetos.size(); i++) {
            System.out.println((i + 1) + " - " + projetos.get(i).getNomeProjeto());
        }
        System.out.println("Escolha o projeto: ");
        int opcaoProjeto = sc.nextInt();
        sc.nextLine();
        if (opcaoProjeto < 0 || opcaoProjeto > projetos.size()) {
            System.out.println("Opção inválida!");
            return;
        }
        Projeto projetoSelecionado = projetos.get(opcaoProjeto - 1);


        List<Tarefa> tarefas = tarefaService.listartarefasPorProjeto(projetoSelecionado);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada nesse projeto!");
            return;
        }
        System.out.println("====LISTA DE TAREFAS===");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + " - " + tarefas.get(i).getNomeTarefa());
        }
        System.out.println("Escolha a tarefa: ");
        int opcaoTarefa = sc.nextInt();
        sc.nextLine();
        if (opcaoTarefa < 0 || opcaoTarefa > tarefas.size()) {
            System.out.println("Opção inválida!");
            return;
        }
        Tarefa tarefaSelecionado = tarefas.get(opcaoTarefa - 1);


        PrioridadeTarefa[] prioridadeTarefas = PrioridadeTarefa.values();
        System.out.println("====TIPOS DE PRIORIDADES====");
        for (int i = 0; i < prioridadeTarefas.length; i++) {
            System.out.println((i + 1) + " - " + prioridadeTarefas[i].toString());
        }
        System.out.println("Escolha o tipo de prioridade: ");
        int opcaoPrioridadeTarefa =  sc.nextInt();
        sc.nextLine();
        if (opcaoPrioridadeTarefa < 0 || opcaoPrioridadeTarefa > prioridadeTarefas.length) {
            System.out.println("Opção inválida!");
            return;
        }
        PrioridadeTarefa prioridadeSelecionada = prioridadeTarefas[opcaoPrioridadeTarefa - 1];
        tarefaService.alterarPrioridadeDiretamenteTarefa(usuario, prioridadeSelecionada, tarefaSelecionado);
        System.out.println("Prioridade alterada com sucesso!");

    }
    private void alterarStatusTarefa() {

        List<Projeto> projetos = projetoService.listaProjetos();
        if (projetos.isEmpty()) {
            System.out.println("Nenhum projeto encontrado!");
            return;
        }
        System.out.println("====LISTA DE PROJETOS====");
        for (int i = 0; i < projetos.size(); i++) {
            System.out.println((i + 1) + " - " + projetos.get(i).getNomeProjeto());
        }
        System.out.println("Escolha o projeto: ");
        int opcaoProjeto = sc.nextInt();
        sc.nextLine();
        if (opcaoProjeto < 0 || opcaoProjeto > projetos.size()) {
            System.out.println("Opção inválida!");
            return;
        }
        Projeto projetoSelecionado = projetos.get(opcaoProjeto - 1);


        List<Tarefa> tarefas = tarefaService.listartarefasPorProjeto(projetoSelecionado);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada nesse projeto!");
            return;
        }
        System.out.println("====LISTA DE TAREFAS===");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + " - " + tarefas.get(i).exibirResumoTarefa());
        }
        System.out.println("Escolha a tarefa: ");
        int opcaoTarefa = sc.nextInt();
        sc.nextLine();
        if (opcaoTarefa < 0 || opcaoTarefa > tarefas.size()) {
            System.out.println("Opção inválida!");
            return;
        }
        Tarefa tarefaSelecionado = tarefas.get(opcaoTarefa - 1);


        StatusTarefa[] statusTarefa = StatusTarefa.values();
        System.out.println("====LISTA DE TIPOS DE STATUS====");
        for (int i = 0; i < statusTarefa.length; i++) {
            System.out.println((i + 1) + " - " + statusTarefa[i].toString());
        }
        System.out.println("Escolha para qual o status deseja alterar: ");
        int opcaoStatusTarefa = sc.nextInt();
        sc.nextLine();
        if (opcaoStatusTarefa < 0 || opcaoStatusTarefa > statusTarefa.length) {
            System.out.println("OpçãO inválida");
            return;
        }
        StatusTarefa statusTarefaSelecionado = statusTarefa[opcaoStatusTarefa - 1];

        tarefaService.alterarStatusTarefa(tarefaSelecionado, statusTarefaSelecionado, usuario);
        System.out.println("StatusTarefa alterada com sucesso!");
    }

    private void solicitarAlteracaoPrioridadeProjeto() {
        List<Projeto> projetos = projetoService.listaProjetos();
        if (projetos.isEmpty()) {
            System.out.println("Nenhum projeto encontrado!");
            return;
        }
        System.out.println("====LISTA DE PROJETOS====");
        for (int i = 0; i < projetos.size(); i++) {
            System.out.println((i + 1) + " - " + projetos.get(i).exibirResumoProjeto());
        }
        System.out.println("Escolha o projeto: ");
        int opcaoProjeto = sc.nextInt();
        sc.nextLine();
        if (opcaoProjeto < 0 || opcaoProjeto > projetos.size()) {
            System.out.println("Opção inválida!");
            return;
        }
        Projeto projetoSelecionado = projetos.get(opcaoProjeto - 1);


        PrioridadeProjeto[] prioridade = PrioridadeProjeto.values();
        System.out.println("====LISTA DE TIPOS DE PRIORIDADES====");
        for (int i = 0; i < prioridade.length; i++) {
            System.out.println((i + 1) + " - " + prioridade[i].toString());
        }
        System.out.println("Escolha para qual prioridade deseja alterar: ");
        int opcaoPrioridade = sc.nextInt();
        sc.nextLine();
        if (opcaoPrioridade < 0 || opcaoPrioridade > prioridade.length) {
            System.out.println("Opção inválida!");
            return;
        }
        PrioridadeProjeto prioridadeProjetoSelecionado = prioridade[opcaoPrioridade - 1];
        String prioridadeProjetoSelecionadoString = String.valueOf(prioridadeProjetoSelecionado);


        System.out.print("Descreva o porque dessa atualização: ");
        String textoComentario = sc.nextLine();
        Comentario comentario = comentarioService.criarComentario(textoComentario, usuario, TipoAcao.ALTERAR);

        solicitacaoAlteracaoService.solicitarAlteracaoPrioridadeProjeto(usuario, projetoSelecionado, prioridadeProjetoSelecionadoString, comentario);
        System.out.println("Solicitação enviada com sucesso!");
    }

    private void listarSolicitacoes() {
        solicitacaoAlteracaoService.ListatSolicitacaoPendenteTipo(TipoAlteracao.STATUS_TAREFA);
    }

    private void analisarSolicitacoes() {
        List<SolicitacaoAlteracao> solicitacaoPendente = solicitacaoAlteracaoService.ListatSolicitacaoPendenteTipo(TipoAlteracao.STATUS_TAREFA);
        if (solicitacaoPendente.isEmpty()) {
            System.out.println("Não há solicitações pendentes.");
            return;
        }
        System.out.println("====SOLICITAÇÃO PENDENTES====");
        for (int i = 0; i < solicitacaoPendente.size(); i++) {
            System.out.println((i + 1) + " - " + solicitacaoPendente.get(i).exibirResumoComentario());
        }
        System.out.println("Escolha uma solicitação: ");
        int opcaoSolicitacao = sc.nextInt();
        sc.nextLine();
        if (opcaoSolicitacao < 0 || opcaoSolicitacao > solicitacaoPendente.size()) {
            System.out.println("Opção inválida!");
            return;
        }
        SolicitacaoAlteracao solicitacaoAlteracaoSelecionada = solicitacaoPendente.get(opcaoSolicitacao - 1);

        System.out.println("====DETALHES DA SOLICITAÇÃO====");
        System.out.println(solicitacaoAlteracaoSelecionada.exibirDetalhesSolicitacao());


        TipoAcao[] tipoAcao = TipoAcao.values();
        for (int i = 0; i < tipoAcao.length; i++) {
            System.out.println((i + 1) + " - " + tipoAcao[i].toString());
        }
        System.out.println("Escolha uma tipo de solicitação: ");
        int opcaoTipoSolicitacao = sc.nextInt();
        sc.nextLine();
        if (opcaoTipoSolicitacao < 0 || opcaoTipoSolicitacao > tipoAcao.length) {
            System.out.println("Opção inválida!");
            return;
        }
        TipoAcao tipoAcaoSelecionado = tipoAcao[opcaoTipoSolicitacao - 1];

        System.out.println("Descreva um comentário sobre a solicitação selecionada: ");
        String textoComentario = sc.nextLine();
        Comentario comentario = comentarioService.criarComentario(textoComentario, usuario, tipoAcaoSelecionado);

        switch(tipoAcaoSelecionado) {
            case APROVADO:
                solicitacaoAlteracaoService.aprovarSolitacao(solicitacaoAlteracaoSelecionada, usuario, comentario);
                System.out.println("Solicitação aprovada com sucesso!");
                break;

            case REJEITADO:
                solicitacaoAlteracaoService.rejeitarSolitacao(solicitacaoAlteracaoSelecionada, usuario, comentario);
                System.out.println("Solicitação rejeitada com sucesso!");
                break;

            case ATUALIZACAO:
                solicitacaoAlteracaoService.atualizarSolitacao(solicitacaoAlteracaoSelecionada, usuario, comentario);
                System.out.println("Solicitação para atualização enviada com sucesso!");
                break;

            case ALTERAR:
                solicitacaoAlteracaoService.alterarSolitacao(solicitacaoAlteracaoSelecionada, usuario, comentario);
                System.out.println("Solicitação para alteração enviada com sucesso!");
                break;

            default:
                throw new ValidacaoException("Valor inválido: " + tipoAcaoSelecionado);
        }
    }
}
