package model.menu;

import model.entities.*;
import model.enums.*;
import model.exceptions.SolicitacaoException;
import model.exceptions.ValidacaoException;
import model.services.*;
import java.util.List;
import java.util.Scanner;

public class MenuGerente {
    private final Scanner sc;
    private final Usuario usuario;
    private final UsuarioService usuarioService;
    private final ProjetoService projetoService;
    private final TarefaService tarefaService;
    private final SolicitacaoAlteracaoService solicitacaoAlteracaoService;
    private final ComentarioService comentarioService;

    public MenuGerente(ProjetoService projetoService, TarefaService tarefaService, SolicitacaoAlteracaoService solicitacaoAlteracaoService, ComentarioService comentarioService, Scanner sc, Usuario usuario, UsuarioService usuarioService) {
        this.projetoService = projetoService;
        this.solicitacaoAlteracaoService = solicitacaoAlteracaoService;
        this.tarefaService = tarefaService;
        this.comentarioService = comentarioService;
        this.sc = sc;
        this.usuario = usuario;
        this.usuarioService = usuarioService;
    }

    public void exibir() {
        while (true) {
            System.out.println("""
                ===== MENU GERENTE =====
                1 - Criar projeto
                2 - Criar tarefa
                3 - Alterar prioridade do projeto
                4 - Alterar prioridade do tarefa
                5 - Alterar status da tarefa
                6 - Analisar Solicitações
                7 - Listar todas as solicitacoes
                8 - Listar solicitações pendentes
                9 - Listar solicitações por tipo
                0 - Sair
                """);

            int opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1 -> criarProjeto();
                case 2 -> criarTarefa();
                case 3 -> alterarPrioridadeProjeto();
                case 4 -> alterarPrioridadeTarefa();
                case 5 -> alterarStatusTarefa();
                case 6 -> analisarSolicitacoes();
                case 7 -> listarTodasSolicitacoes();
                case 8 -> listarSolicitacoesPendentes();
                case 9 -> listarSolicitacoesPendentesTipo();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida");
            }
        }
    }
    private void criarProjeto() {
        System.out.println("Digite o nome do projeto: ");
        String nomeProjeto = sc.nextLine();
        projetoService.criarProjeto(nomeProjeto, usuario);
        System.out.println("Projeto criado com sucesso!");
    }

    private void criarTarefa() {
        List<Projeto> projetos = projetoService.listaProjetos();
        if (projetos.isEmpty()) {
            System.out.println("Não há projetos cadastrados.");
            return;
        }
        System.out.println("====LISTA DE PROJETOS====");
        for (int i = 0 ; i < projetos.size() ; i++) {
            System.out.print((i + 1) + " - " + projetos.get(i).getNomeProjeto());
        }
        System.out.println("Escolha o projeto: ");
        int opcaoProjeto = sc.nextInt();
        sc.nextLine();
        if(opcaoProjeto < 0 || opcaoProjeto > projetos.size()) {
            System.out.print("Opção inválida.");
            return;
        }
        Projeto projetoSelecionado = projetos.get(opcaoProjeto - 1);


        List<Usuario> responsavelTarefa = usuarioService.listarUsuarioPodemReceberTarefas();
        if (responsavelTarefa.isEmpty()) {
            System.out.println("Nenhum usuário disponível para receber tarefas");
        }
        System.out.println("====LISTA DE USUÁRIOS DISPONIVEIS====");
        for (int i = 0; i <responsavelTarefa.size(); i++) {
            System.out.print((i + 1) + " - " + responsavelTarefa.get(i).getNome());
        }
        System.out.println("Escolha o responsável: ");
        int opcaoUsuario = sc.nextInt();
        sc.nextLine();
        if(opcaoUsuario < 1 || opcaoUsuario > responsavelTarefa.size()) {
            System.out.print("Opção inválida.");
            return;
        }
        Usuario responsavel = responsavelTarefa.get(opcaoUsuario - 1);


        System.out.println("Digite o nome da tarefa: ");
        String nomeTarefa = sc.nextLine();
        System.out.println("Escreva a descrição da tarefa: ");
        String descricaoTarefa = sc.nextLine();

        tarefaService.criarTarefa(projetoSelecionado, nomeTarefa, descricaoTarefa, responsavel, usuario);
        System.out.println("Tarefa criada com sucesso!");
    }
    private void alterarPrioridadeProjeto() {

        List<Projeto> projetos = projetoService.listaProjetos();
        if (projetos.isEmpty()) {
            System.out.println("Não há projetos cadastrados.");
            return;
        }
        System.out.println("====LISTA DE PROJETOS====");
        for (int i = 0; i < projetos.size(); i++) {
            System.out.print((i + 1) + " - " + projetos.get(i).getNomeProjeto());
        }
        System.out.println("Escolha o projeto: ");
        int opcaoProjeto = sc.nextInt();
        sc.nextLine();
        if(opcaoProjeto < 1 || opcaoProjeto > projetos.size()) {
            System.out.print("Opção inválida.");
            return;
        }
        Projeto projetoSelecionado = projetos.get(opcaoProjeto - 1);


        PrioridadeProjeto[] prioridade = PrioridadeProjeto.values();
        System.out.println("====TIPOS DE PRIORIDADES DE PROJETOS====");
        for(int i = 0; i < prioridade.length; i++) {
            System.out.println((i + 1) + " - " + prioridade[i]);
        }
        System.out.println("Para qual prioridade você deseja alterar o projeto: ");
        int opcaoPrioridade = sc.nextInt();
        sc.nextLine();
        if(opcaoPrioridade < 1 || opcaoPrioridade > prioridade.length) {
            System.out.print("Opção inválida.");
            return;
        }
        PrioridadeProjeto prioridadeSelecionada = prioridade[opcaoPrioridade - 1];
        projetoService.alterarPrioridadeDiretaProjeto(projetoSelecionado, usuario, prioridadeSelecionada);
        System.out.println("Alteração realizada com sucesso!");
    }
    private void alterarPrioridadeTarefa() {
        List<Projeto> projetos = projetoService.listaProjetos();
        if (projetos.isEmpty()) {
            System.out.println("Não há projetos cadastrados.");
            return;
        }
        System.out.println("====LISTA DE PROJETOS====");
        for (int i = 0; i < projetos.size(); i++) {
            System.out.print((i + 1) + " - " + projetos.get(i).getNomeProjeto());
        }
        System.out.println("Escolha o projeto: ");
        int opcaoProjeto = sc.nextInt();
        sc.nextLine();
        if(opcaoProjeto < 1 || opcaoProjeto > projetos.size()) {
            System.out.print("Opção inválida.");
            return;
        }
        Projeto projetoSelecionado = projetos.get(opcaoProjeto - 1);


        List<Tarefa> tarefas = tarefaService.listartarefasPorProjeto(projetoSelecionado);
        if (tarefas.isEmpty()) {
            System.out.println("Este projeto não possui tarefas.");
        }
        System.out.println("====LISTA DE TAREFAS====");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.print((i + 1) + " - " + tarefas.get(i).getNomeTarefa());
        }
        System.out.println("Escolha a tarefa: ");
        int opcaoTarefa = sc.nextInt();
        sc.nextLine();
        if(opcaoTarefa < 1 || opcaoTarefa > tarefas.size()) {
            System.out.print("Opção inválida.");
            return;
        }
        Tarefa tarefaSelecionada = tarefas.get(opcaoTarefa - 1);


        PrioridadeTarefa[] prioridade = PrioridadeTarefa.values();
        System.out.println("====TIPOS DE PRIORIDADES DE TAREFAS====");
        for (int i = 0; i < prioridade.length; i++) {
            System.out.println((i + 1) + " - " + prioridade[i]);
        }
        System.out.println("Para qual prioridade você deseja alterar a tarefa: ");
        int opcaoPrioridade = sc.nextInt();
        sc.nextLine();
        if(opcaoPrioridade < 1 || opcaoPrioridade > prioridade.length) {
            System.out.print("Opção inválida.");
            return;
        }
        PrioridadeTarefa prioridadeSelecionada = prioridade[opcaoPrioridade - 1];
        tarefaService.alterarPrioridadeDiretamenteTarefa(usuario, prioridadeSelecionada, tarefaSelecionada);
        System.out.println("Alteração realizada com sucesso!");
    }
    private void alterarStatusTarefa() {
        List<Projeto> projetos = projetoService.listaProjetos();
        if (projetos.isEmpty()) {
            System.out.println("Não há projetos cadastrados.");
            return;
        }
        System.out.println("====LISTA DE PROJETOS====");
        for ( int i = 0; i < projetos.size(); i++) {
            System.out.print((i + 1) + " - " + projetos.get(i).getNomeProjeto());
        }
        System.out.println("Escolha o projeto: ");
        int opcaoProjeto = sc.nextInt();
        sc.nextLine();
        if(opcaoProjeto < 1 || opcaoProjeto > projetos.size()) {
            System.out.print("Opção inválida.");
            return;
        }
        Projeto projetoSelecionado = projetos.get(opcaoProjeto - 1);


        List<Tarefa> tarefas = tarefaService.listartarefasPorProjeto(projetoSelecionado);
        if (tarefas.isEmpty()) {
            System.out.println("Este projeto não possui tarefas.");
        }
        System.out.println("====LISTA DE TAREFAS====");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.print((i + 1) + " - " + tarefas.get(i).getNomeTarefa());
        }
        System.out.println("Escolha a tarefa: ");
        int opcaoTarefa = sc.nextInt();
        sc.nextLine();
        if(opcaoTarefa < 1 || opcaoTarefa > tarefas.size()) {
            System.out.print("Opção inválida.");
            return;
        }
        Tarefa tarefaSelecionada = tarefas.get(opcaoTarefa - 1);


        StatusTarefa [] statusTarefa = StatusTarefa.values();
        System.out.println("====TIPOS DE STATUS DE TAREFA====");
        for (int i = 0; i < statusTarefa.length; i++) {
            System.out.println((i + 1) + " - " + statusTarefa[i]);
        }
        System.out.println("Escolha o tipo de status: ");
        int opcaoStatusTarefa = sc.nextInt();
        sc.nextLine();
        if(opcaoStatusTarefa < 0 || opcaoStatusTarefa > statusTarefa.length) {
            System.out.print("Opção inválida.");
            return;
        }
        StatusTarefa statusSelecionado = statusTarefa[opcaoStatusTarefa - 1];
        tarefaService.alterarStatusDiretamenteTarefa(usuario, statusSelecionado, tarefaSelecionada);
        System.out.println("Alteração realizada com sucesso!");
    }
    private void listarTodasSolicitacoes() {
        solicitacaoAlteracaoService.exibirSolicitacoes(solicitacaoAlteracaoService.listarTodas());
    }
    private void listarSolicitacoesPendentes() {
        solicitacaoAlteracaoService.listarSolicitacoesPendentes();
    }
    private void listarSolicitacoesPendentesTipo() {
        TipoAlteracao[]  tipoAlteracao = TipoAlteracao.values();
        System.out.println("====TIPOS DE ALTERAÇÃO====");
        for (int i = 0; i < tipoAlteracao.length; i++) {
            System.out.println((i + 1) + " - " + tipoAlteracao[i]);
        }
        System.out.println("Escolha o tipo de alteração: ");
        int opcaoSolicitacao = sc.nextInt();
        sc.nextLine();
        if(opcaoSolicitacao < 1 || opcaoSolicitacao > tipoAlteracao.length) {
            System.out.print("Opção inválida.");
            return;
        }
        TipoAlteracao tipoAlteracaoSelecionada = tipoAlteracao[opcaoSolicitacao - 1];
        solicitacaoAlteracaoService.ListatSolicitacaoPendenteTipo(tipoAlteracaoSelecionada);
    }
    private void analisarSolicitacoes() {
        List<SolicitacaoAlteracao> solicitacaoPendentes = solicitacaoAlteracaoService.listarSolicitacoesPendentes();
        if(solicitacaoPendentes.isEmpty()) {
            System.out.println("Não há solicitações pendentes.");
            return;
        }


        System.out.println("====SOLICITAÇÕES PENDENTES====");
        for(int i = 0; i < solicitacaoPendentes.size(); i++) {
            System.out.println((i +1) + " - " + solicitacaoPendentes.get(i).exibirResumoComentario());
        }
        System.out.println("Escolha uma solicitação: ");
        int opcaoSolicitacao = sc.nextInt();
        sc.nextLine();
        if(opcaoSolicitacao < 1 || opcaoSolicitacao > solicitacaoPendentes.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        SolicitacaoAlteracao solicitacaoSelecionado = solicitacaoPendentes.get(opcaoSolicitacao - 1);

        System.out.println("====DETALHES DA SOLICITAÇÃO====");
        System.out.print(solicitacaoSelecionado.exibirDetalhesSolicitacao());


        TipoAcao [] tipoDeAcao = TipoAcao.values();
        for (int i = 0; i < tipoDeAcao.length; i++) {
            System.out.println((i + 1) + " - " + tipoDeAcao[i]);
        }
        System.out.println("Escolha o tipo de ação: ");
        int opcaoAcao = sc.nextInt();
        sc.nextLine();
        if(opcaoAcao < 1 || opcaoAcao > tipoDeAcao.length) {
            System.out.println("Opção inválida.");
            return;
        }
        TipoAcao tipoAcaoSelecionado = tipoDeAcao[opcaoAcao - 1];


        System.out.println("Descreva um comentário sobre a solicitação selecionada: ");
        String textoComentario = sc.nextLine();
        Comentario comentario = comentarioService.criarComentario(textoComentario, usuario, tipoAcaoSelecionado);

        switch (tipoAcaoSelecionado) {
            case APROVADO:
                solicitacaoAlteracaoService.aprovarSolitacao(solicitacaoSelecionado, usuario, comentario);
                System.out.println("Solicitação aprovada com sucesso!");
                break;

            case REJEITADO:
                solicitacaoAlteracaoService.rejeitarSolitacao(solicitacaoSelecionado, usuario, comentario);
                System.out.println("Solicitação rejeitada com sucesso!");
                break;

            case ALTERAR:
                solicitacaoAlteracaoService.alterarSolitacao(solicitacaoSelecionado, usuario, comentario);
                System.out.println("Solicitação para alteração enviada com sucesso!");
                break;
            case ATUALIZACAO:
                solicitacaoAlteracaoService.atualizarSolitacao(solicitacaoSelecionado, usuario, comentario);
                System.out.println("Solicitação para atualização enviada com sucesso!");
                break;
            default:
                throw new ValidacaoException("Valor inválido: " + tipoAcaoSelecionado);
        }
    }
}