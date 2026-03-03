package model.services;

import model.entities.Comentario;
import model.entities.Usuario;
import model.enums.TipoAcao;
import model.exceptions.ComentarioException;

import java.util.ArrayList;
import java.util.List;

public class ComentarioService {
    private final FileService fileService;
    private final List<Comentario> comentarios;

    public ComentarioService(FileService fileService, List<Comentario> comentarios) {
        this.fileService = fileService;
        this.comentarios = comentarios;
    }

    public Comentario criarComentario(String textoComentario, Usuario autor, TipoAcao tipoAcao) {

        if (textoComentario == null || textoComentario.isBlank()) {
            throw new ComentarioException("Comentário não pode ser vazio");
        }

        Comentario comentario = new Comentario(textoComentario, autor, tipoAcao);
        comentarios.add(comentario);
        fileService.salvarComentarios(comentario);

        return comentario;
    }

    public List<Comentario> listarComentarios() {
        return new ArrayList<>(comentarios);
    }
    public List<Comentario> buscarComentariosPorSolicitacao(String idSolicitacao) {
        List<Comentario> lista = new ArrayList<>();

        for (Comentario c : comentarios) {
            if (c.getIdSolicitacao().equals(idSolicitacao)) {
                lista.add(c);
            }
        }
        return lista;
    }
}


