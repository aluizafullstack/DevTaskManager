package model.entities;

import model.enums.TipoAcao;
import model.exceptions.ComentarioException;
import model.exceptions.UsuarioException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Comentario {
    private String idComentario;
    private String idSolicitacao;
    private String textoComentario;
    private Usuario autor;
    private String dataHora;
    private TipoAcao tipoAcao;

    public Comentario(String textoComentario, Usuario autor, TipoAcao tipoAcao) {
        this.textoComentario = textoComentario;
        this.autor = autor;
        this.tipoAcao = tipoAcao;
        this.dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.idComentario = UUID.randomUUID().toString().substring(0, 8);
        this.idSolicitacao = UUID.randomUUID().toString().substring(0, 8);
    }

    protected Comentario(String idComentario, String idSolicitacao, String textoComentario, Usuario autor, String dataHora, TipoAcao tipoAcao) {
        this.idComentario = idComentario;
        this.idSolicitacao = idSolicitacao;
        this.textoComentario = textoComentario;
        this.autor = autor;
        this.dataHora = dataHora;
        this.tipoAcao = tipoAcao;
    }

    // GETTERS

    public String getIdComentario() {
        return idComentario;
    }

    public String getIdSolicitacao() {
        return idSolicitacao;
    }

    public String getTextoComentario() {
        return textoComentario;
    }

    public Usuario getAutor() {
        return autor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public TipoAcao getTipoAcao() {
        return tipoAcao;
    }

    public String exibirResumoComentario() {
        return String.format(
                "ID: %s | Autor: %s | Data: %s | Tipo de ação: %s",
                idComentario,
                autor.getNome(),
                dataHora,
                tipoAcao
        );
    }

    public String exibirDetalhesComentario() {
        return """
                ===============================
                Comentário
                -------------------------------
                ID: %s
                Autor: %s
                Comentário: %s
                Data/Hora: %s
                Tipo de ação: %s
                ===============================
               """.formatted(
                idComentario,
                autor.getNome(),
                textoComentario,
                dataHora,
                tipoAcao
        );
    }
    @Override
    public String toString() {
        return "Comentario{" +
                "ID='" + idComentario + '\'' +
                "data/hora=" + dataHora +
                "tipo=" + tipoAcao +
                '}';
    }

    public String toFileString() {
        return idComentario + ";" +
                idSolicitacao + ";" +
                autor.getNome() + ";" +
                textoComentario + ";" +
                dataHora + ";" +
                tipoAcao;
    }


    public static Comentario fromFileString(String linha, List<Usuario> usuarios) {

        String[] dados = linha.split(";");

        String idComentario = dados[0];
        String  idSolicitacao = dados[1];
        String nomeAutor = dados[2];
        String texto = dados[3];
        String dataHora = dados[4];
        TipoAcao tipo = TipoAcao.valueOf(dados[5]);

        Usuario autor = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nomeAutor)) {
                autor = usuario;
                break;
            }
        }

        if (autor == null) {
            throw new UsuarioException("Autor do comentário não encontrado");
        }

        return new Comentario(idComentario, idSolicitacao, texto, autor, dataHora, tipo);
    }

}