
package br.com.library_api.service;

import br.com.library_api.dto.request.LivroRequestDTO;
import br.com.library_api.dto.response.LivroResponseDTO;
import br.com.library_api.model.Livro;
import br.com.library_api.repository.LivroRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository repository;

    @InjectMocks
    private LivroService service;

    @Test
    void deveCadastrarLivro() {

        // ARRANGE - Preparação

        LivroRequestDTO request = new LivroRequestDTO(
                "O Pequeno Príncipe",
                "9780156012195",
                "Uma história sobre amizade",
                1943,
                "Antoine de Saint-Exupéri"
        );

        when(repository.save(any(Livro.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        // ACT - Execução

        LivroResponseDTO response = service.cadastrar(request);


        // ASSERT - Verificação

        ArgumentCaptor<Livro> captor =
                ArgumentCaptor.forClass(Livro.class);

        verify(repository).save(captor.capture());

        Livro livroEnviado = captor.getValue();

        assertEquals("O Pequeno Príncipe", livroEnviado.getTitulo());
        assertEquals("9780156012195", livroEnviado.getIsbn());
        assertEquals("Uma história sobre amizade", livroEnviado.getDescricao());
        assertEquals(1943, livroEnviado.getAnoPublicacao());
        assertEquals("Antoine de Saint-Exupéri", livroEnviado.getAutor());

        assertNotNull(response);
    }
}
