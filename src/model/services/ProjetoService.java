package model.services;

import model.entities.Projeto;
import model.entities.Usuario;
import model.enums.PrioridadeProjeto;
import model.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class ProjetoService {

    private List<Projeto> projetos;
    private final FileService fileService;

    public ProjetoService (FileService fileService, List<Projeto> projetos) {
        this.fileService = fileService;
        this.projetos = projetos;
    }
    public Projeto criarProjeto (String nomeProjeto, Usuario usuario) {
        if (!usuario.podeCriarProjeto()) {
            throw new PermissaoException("Usuario não tem permissão para criar projeto");
        }
        if(buscarProjetoPorNome(nomeProjeto) != null) {
            throw new ProjetoException("Já existe um projeto com o nome informado");
        }
        Projeto projeto = new Projeto(nomeProjeto, usuario);
        projetos.add(projeto);
        fileService.salvarProjetos(projeto);
        return projeto;
    }
    public void alterarPrioridadeDiretaProjeto (Projeto projeto, Usuario usuario, PrioridadeProjeto novaPrioridadeProjeto) {
        if (!usuario.podeAlterarPrioridadeDiretaProjeto()) {
            throw new ProjetoException("Usuario não pode alterar a prioridade do projeto");
        }

        if(novaPrioridadeProjeto == projeto.getPrioridadeProjeto()) {
            throw new ProjetoException("O projeto já está com essa prioridade");
        }
        Projeto nome = buscarProjetoPorNome(projeto.getNomeProjeto());
        if (nome == null) {
            throw new ProjetoException("Projeto não existe");
        }
        nome.setPrioridadeProjeto(novaPrioridadeProjeto);
        fileService.reescreverProjetos(projetos);
    }
    public Projeto buscarProjetoPorNome(String nomeProjeto) {
        for (Projeto projeto : projetos) {
            if (projeto.getNomeProjeto().equalsIgnoreCase(nomeProjeto)) {
                return projeto;
            }
        }
        return null;
    }
    public List<Projeto> listaProjetos() {
        return new ArrayList<>(projetos);
    }
}