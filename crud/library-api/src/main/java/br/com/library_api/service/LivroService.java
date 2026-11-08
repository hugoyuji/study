package br.com.library_api.service;

import br.com.library_api.dto.request.LivroRequestDTO;
import br.com.library_api.dto.response.LivroResponseDTO;
import br.com.library_api.model.Livro;
import br.com.library_api.repository.LivroRepository;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public LivroResponseDTO cadastrar(LivroRequestDTO livroRequest) {

        Livro livroEntidade = new Livro();

        livroEntidade.setTitulo(livroRequest.titulo());
        livroEntidade.setIsbn(livroRequest.isbn());
        livroEntidade.setDescricao(livroRequest.descricao());
        livroEntidade.setAnoPublicacao(livroRequest.anoPublicacao());
        livroEntidade.setAutor(livroRequest.autor());

        Livro livroSalvo = repository.save(livroEntidade);

        return LivroResponseDTO.from(livroSalvo);
    }

    public LivroResponseDTO buscarPorId(Long id) {

        Livro livroEntidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado."));

        return LivroResponseDTO.from(livroEntidade);
    }

}
