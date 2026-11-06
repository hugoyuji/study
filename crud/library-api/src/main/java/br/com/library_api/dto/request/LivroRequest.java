package br.com.library_api.dto.request;

import jakarta.validation.constraints.*;

public record LivroRequest(

        @NotBlank(message = "O título é obrigatório.")
        @Size(max = 255, message = "O título deve ter no máximo 255 caracteres.")
        String titulo,

        @NotBlank(message = "O ISBN é obrigatório.")
        @Pattern(regexp = "^(\\d{13}|\\d{9}[\\dX])$", message = "ISBN inválido. Informe um ISBN-10 ou ISBN-13 válido.")
        String isbn,

        @Size(max = 2000, message = "A descrição deve ter no máximo 2.000 caracteres.")
        String descricao,

        @NotNull(message = "O ano de publicação é obrigatório.")
        @Min(value = 1450, message = "O ano de publicação deve ser a partir de 1450.")
        Integer anoPublicacao,

        @NotBlank(message = "O autor é obrigatório.")
        @Size(max = 255, message = "O nome do autor deve ter no máximo 255 caracteres.")
        String autor
) {

        public LivroRequest {
                titulo = titulo == null ? null : titulo.strip();
                isbn = isbn == null ? null : isbn.strip().replaceAll("[\\s-]", "").toUpperCase();
                descricao = descricao == null || descricao.isBlank() ? null : descricao.strip();
                autor = autor == null ? null : autor.strip();
        }
}