package br.com.library_api.dto.response;

import br.com.library_api.model.Livro;

import java.time.Instant;

public record LivroResponseDTO(
        Long id,
        String titulo,
        String isbn,
        String descricao,
        Integer anoPublicacao,
        String autor,
        Instant dataCadastro,
        Instant dataAlteracao
) {

    public static LivroResponseDTO from(Livro livro) {
        return new LivroResponseDTO(
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
