package model.services;

import model.entities.Coordenador;
import model.entities.Desenvolvedor;
import model.entities.Gerente;
import model.entities.Usuario;
import model.exceptions.UsuarioException;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final List<Usuario> usuarios;
    private final FileService fileService;

    public UsuarioService(FileService fileService, List<Usuario> usuarios) {
        this.fileService = fileService;
        this.usuarios = usuarios;
    }
    public Usuario criarUsuario(String nome, String email, String senha, String tipoUsuario) {
        if (emailExiste(email)) {
            throw new UsuarioException("Email já cadastrado");
        }
        Usuario usuario;
        switch(tipoUsuario.toUpperCase()) {
            case "GERENTE":
                usuario = new Gerente(nome, email, senha);
                break;
            case "COORDENADOR":
                usuario = new Coordenador(nome, email, senha);
                break;
            case "DESENVOLVEDOR":
                usuario = new Desenvolvedor(nome, email, senha);
                break;
            default:
                throw new UsuarioException("Tipo de usuário inválido");
        }
        usuarios.add(usuario);
        fileService.salvarUsario(usuario);
        return usuario;
    }
    public Usuario login(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if(usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario;

            }
        }
        throw new UsuarioException("Email ou senha inválidos");
        
    }
    private boolean emailExiste(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    private Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public void alterarSenha(String email, String senhaAtual, String novaSenha) {

        Usuario usuario = buscarUsuarioPorEmail(email);

        if (usuario == null) {
            throw new UsuarioException("Usuário não encontrado");
        }
        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new UsuarioException("Senha atual incorreta");
        }
        if (senhaAtual.equals(novaSenha)) {
            throw new UsuarioException("A nova senha deve ser diferente da atual");
        }

        usuario.setSenha(novaSenha);
        fileService.reescreverUsuario(usuarios);
    }

    public List<Usuario> listarUsuarioPodemReceberTarefas() {
        List<Usuario> responsaveis = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.podeReceberTarefa()) {
                responsaveis.add(usuario);
            }
        }
        return responsaveis;
    }
}