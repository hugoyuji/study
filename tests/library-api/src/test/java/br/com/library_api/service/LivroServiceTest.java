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

import java.util.Optional;

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

        LivroRequestDTO request = new LivroRequestDTO(
                "O Pequeno Príncipe",
                "9780156012195",
                "Uma história sobre amizade",
                1943,
                "Antoine de Saint-Exupéri"
        );

        when(repository.save(any(Livro.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LivroResponseDTO response = service.cadastrar(request);

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

    @Test
    void deveBuscarLivroPorId() {

        Livro livro = new Livro();
        livro.setTitulo("O Pequeno Príncipe");
        livro.setIsbn("9780156012195");
        livro.setDescricao("Uma história sobre amizade");
        livro.setAnoPublicacao(1943);
        livro.setAutor("Antoine de Saint-Exupéri");

        when(repository.findById(1L)).thenReturn(Optional.of(livro));

        LivroResponseDTO response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("O Pequeno Príncipe", response.titulo());
        assertEquals("9780156012195", response.isbn());
        assertEquals("Uma história sobre amizade", response.descricao());
        assertEquals(1943, response.anoPublicacao());
        assertEquals("Antoine de Saint-Exupéri", response.autor());

        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEncontrado() {

        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.buscarPorId(99L));

        assertEquals("Livro não encontrado.", exception.getMessage());

        verify(repository).findById(99L);
    }
}