package model.services;

import model.entities.*;
import model.exceptions.FileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String CAMINHO_BASE = "dados/";

    public void salvarUsario(Usuario usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_BASE + "usuarios.txt", true))) {

            bw.write(usuarios.toFileString());
            bw.newLine();

        } catch (IOException e) {
            throw new FileException("Erro ao salvar usuarios");
        }
    }
    public List<Usuario> carregarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_BASE + "usuarios.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                usuarios.add(Usuario.fromFileString(linha));
            }

        } catch (IOException e) {
            throw new FileException("Erro ao carregar usuarios");
        }
        return usuarios;
    }

    public void reescreverUsuario(List<Usuario> usuarios) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_BASE + "usuarios.txt"))) {
            for (Usuario usuario: usuarios) {
                bw.write(usuario.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new FileException("Erro ao atualizar usuarios");
        }

    }

    public void salvarProjetos(Projeto projetos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_BASE + "projetos.txt", true))) {
            bw.write(projetos.toFileString());
            bw.newLine();

        } catch (IOException e) {
            throw new FileException("Erro ao salvar projetos", e);
        }

    }
    public List<Projeto> carregarProjetos(List<Usuario> usuarios) {
        List<Projeto> projetos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader (new FileReader(CAMINHO_BASE + "projetos.txt"))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                projetos.add(Projeto.fromFileString(linha, usuarios));
            }

        } catch (IOException e) {
            throw new FileException("Erro ao carregar projetos", e);
        }
        return projetos;
    }

    public void reescreverProjetos(List<Projeto> projetos) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_BASE + "projetos.txt"))){
            for (Projeto projeto : projetos) {
                bw.write(projeto.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new FileException("Erro ao atualizar projetos", e);
        }
    }
    public void salvarTarefas(Tarefa tarefa) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_BASE + "tarefas.txt", true))) {
            bw.write(tarefa.toFileString());
            bw.newLine();

        } catch (IOException e) {
            throw new FileException("Erro ao salvar tarefas", e);
        }
    }

    public void reescreverTarefas(List<Tarefa> tarefas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_BASE + "tarefas.txt"))) {
            for (Tarefa tarefa : tarefas) {
                bw.write(tarefa.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new FileException("Erro ao atualizar tarefas", e);
        }

    }
    public List<Tarefa> carregarTarefas(List<Usuario> usuarios, List<Projeto> projetos) {
        List<Tarefa> tarefas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_BASE + "tarefas.txt"))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                tarefas.add(Tarefa.fromFileString(linha, usuarios, projetos));
            }
        } catch (IOException e) {
            throw new FileException("Erro ao carregar tarefas", e);
        }
        return tarefas;
    }
    public void salvarSolicitacoes(SolicitacaoAlteracao solicitacaoAlteracao) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_BASE + "solicitacoes.txt", true))) {
            bw.write(solicitacaoAlteracao.toFileString());
            bw.newLine();

        } catch (IOException e) {
            throw new FileException("Erro ao salvar Solicitações", e);
        }

    }
    public List<SolicitacaoAlteracao> carregarSolicitacoes(List<Usuario> usuarios, List<Projeto> projetos, List<Tarefa> tarefas, List<Comentario> comentarios) {
        List<SolicitacaoAlteracao> solicitacoesAlteracao = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(CAMINHO_BASE + "solicitacoes.txt"))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                solicitacoesAlteracao.add(SolicitacaoAlteracao.fromFileString(linha, usuarios, projetos, tarefas, comentarios));
            }

        } catch (IOException e) {
            throw new FileException("Erro ao carregar Solicitações", e);
        }
        return solicitacoesAlteracao;
    }

    public void reescreverSolitacoes(List<SolicitacaoAlteracao> solicitacoes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_BASE + "solicitacoes.txt"))) {
            for (SolicitacaoAlteracao solicitacaoAlteracao : solicitacoes) {
                bw.write(solicitacaoAlteracao.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new FileException("Erro ao atualizar Solicitações", e);
        }

    }
    public void salvarComentarios(Comentario comentario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_BASE + "comentarios.txt", true))) {
            bw.write(comentario.toFileString());
            bw.newLine();

        } catch (IOException e) {
            throw new FileException("Erro ao salvar Comentarios", e);
        }
    }
    public List<Comentario> carregarComentarios(List<Usuario> usuarios) {
        List<Comentario> comentarios = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_BASE + "comentarios.txt"))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                comentarios.add(Comentario.fromFileString(linha, usuarios));
            }
        } catch (IOException e) {
            throw new FileException("Erro ao carregar Comentarios", e);
        }
        return comentarios;
    }

    public void reescreverComentarios(List<Comentario> comentarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_BASE + "comentarios.txt"))) {
            for (Comentario comentario : comentarios) {
                bw.write(comentario.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new FileException("Erro ao atualizar Comentarios", e);
        }
    }
}