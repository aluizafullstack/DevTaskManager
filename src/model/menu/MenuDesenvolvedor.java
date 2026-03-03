package model.menu;

import model.entities.Comentario;
import model.entities.Tarefa;
import model.entities.Usuario;
import model.enums.StatusTarefa;
import model.enums.TipoAcao;
import model.services.ComentarioService;
import model.services.SolicitacaoAlteracaoService;
import model.services.TarefaService;

import java.util.List;
import java.util.Scanner;

public class MenuDesenvolvedor {
    private final Scanner sc;
    private final Usuario usuario;
    private final TarefaService tarefaService;
    private final SolicitacaoAlteracaoService solicitacaoAlteracaoService;
    private final ComentarioService comentarioService;

    public MenuDesenvolvedor(Scanner sc, Usuario usuario, TarefaService tarefaService, SolicitacaoAlteracaoService solicitacaoAlteracaoService, ComentarioService comentarioService) {
        this.sc = sc;
        this.usuario = usuario;
        this.tarefaService = tarefaService;
        this.solicitacaoAlteracaoService = solicitacaoAlteracaoService;
        this.comentarioService = comentarioService;
    }

    public void exibir() {
        while (true) {
            System.out.println("""
                    ===== MENU DESENVOLVEDOR =====
                    1 - Listar demandas em andamento
                    2 - Listar demandas ainda não iniciadas
                    3 - Solicitar alteração de status tarefa
                    0 - Sair
                    """);

            int opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1 -> listarDemandasAndamento();
                case 2 -> listarDemandasNaoIniciadas();
                case 3 -> solicitarAlteracaoStatusTarefa();
                case 0 -> {return; }
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private void listarDemandasAndamento() {
        List<Tarefa> tarefas = tarefaService.listarTarefaAndamento(usuario);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa foi encontrada!");
            return;
        }

        System.out.println("====LISTA DE DEMANDAS=====");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + " - " + tarefas.get(i).exibirResumoTarefa());
        }
        System.out.println("Escolha uma tarefa para exibir os detalhes: ");
        int opcaoTarefa = sc.nextInt();
        sc.nextLine();
        if (opcaoTarefa < 0 || opcaoTarefa > tarefas.size()) {
            System.out.println("Opção invalida!");
            return;
        }
        Tarefa tarefaSelecionada = tarefas.get(opcaoTarefa - 1);

        System.out.println("====DETALHES DA TAREFA====");
        System.out.println(tarefaSelecionada.exibirDetalhesTarefa());

    }
    private void listarDemandasNaoIniciadas() {
        List<Tarefa> tarefas = tarefaService.listarTarefaAberta(usuario);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa foi encontrada!");
            return;
        }

        System.out.println("====LISTA DE DEMANDAS=====");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + " - " + tarefas.get(i).exibirResumoTarefa());
        }
        System.out.println("Escolha uma tarefa para exibir os detalhes: ");
        int opcaoTarefa = sc.nextInt();
        sc.nextLine();
        if (opcaoTarefa < 0 || opcaoTarefa > tarefas.size()) {
            System.out.println("Opção invalida!");
            return;
        }
        Tarefa tarefaSelecionada = tarefas.get(opcaoTarefa - 1);

        System.out.println("====DETALHES DA TAREFA====");
        System.out.println(tarefaSelecionada.exibirDetalhesTarefa());

    }
    private void solicitarAlteracaoStatusTarefa() {
        List<Tarefa> tarefas = tarefaService.buscarTarefaporNomeUsuario(usuario);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa foi encontrada!");
            return;
        }
        System.out.println("====LISTAR TAREFAS====");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + " - " +  tarefas.get(i).exibirResumoTarefa());
        }
        System.out.println("Escolha a tarefa que deseja solicitar alteração de status: ");
        int opcaoTarefa = sc.nextInt();
        sc.nextLine();
        if (opcaoTarefa < 0 || opcaoTarefa > tarefas.size()) {
            System.out.println("Opção invalida!");
            return;
        }
        Tarefa tarefaSelecionada = tarefas.get(opcaoTarefa - 1);


        StatusTarefa[] statusTarefa = StatusTarefa.values();
        System.out.println("====LISTAR TIPOS DE STATUS====");
        for (int i = 0; i < statusTarefa.length; i++) {
            System.out.println((i + 1) + " - " + statusTarefa[i].toString());
        }
        System.out.println("Escolha para qual status deseja alterar: ");
        int opcaoStatus = sc.nextInt();
        sc.nextLine();
        if (opcaoStatus < 0 || opcaoStatus > statusTarefa.length) {
            System.out.println("Opção invalida!");
            return;
        }
        StatusTarefa statusTarefaSelecionada = statusTarefa[opcaoStatus - 1];
        String statusTarefaSelecionadaString = String.valueOf(statusTarefaSelecionada);


        System.out.println("Descreva o porque dessa atualização: ");
        String textoComentario = sc.nextLine();
        Comentario comentario = comentarioService.criarComentario(textoComentario, usuario, TipoAcao.ALTERAR);

        solicitacaoAlteracaoService.solicitarAlteracaoStatusTarefa(usuario, tarefaSelecionada,  statusTarefaSelecionadaString, comentario);
        System.out.println("Solicitação enviada com sucesso!");
    }
}
