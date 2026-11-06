package br.com.library_api.dto.response;

import br.com.library_api.model.Livro;

import java.time.Instant;

public record LivroResponse(
        Long id,
        String titulo,
        String isbn,
        String descricao,
        Integer anoPublicacao,
        String autor,
        Instant dataCadastro,
        Instant dataAlteracao
) {

    public static LivroResponse from(Livro livro) {
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getDescricao(),
                livro.getAnoPublicacao(),
                livro.getAutor(),
                livro.getDataCadastro(),
                livro.getDataAlteracao()
        );
    }
}
