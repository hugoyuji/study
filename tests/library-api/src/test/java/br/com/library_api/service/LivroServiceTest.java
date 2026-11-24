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

import java.util.List;
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

    @Test
    void deveListarTodosOsLivros() {

        Livro livro1 = new Livro();
        livro1.setTitulo("O Pequeno Príncipe");
        livro1.setIsbn("9780156012195");
        livro1.setDescricao("Uma história sobre amizade");
        livro1.setAnoPublicacao(1943);
        livro1.setAutor("Antoine de Saint-Exupéri");

        Livro livro2 = new Livro();
        livro2.setTitulo("Dom Casmurro");
        livro2.setIsbn("9788535914849");
        livro2.setDescricao("Romance sobre ciúme e dúvida");
        livro2.setAnoPublicacao(1899);
        livro2.setAutor("Machado de Assis");

        when(repository.findAll()).thenReturn(List.of(livro1, livro2));

        List<LivroResponseDTO> response = service.listar();

        assertNotNull(response);
        assertEquals(2, response.size());

        assertEquals("O Pequeno Príncipe", response.get(0).titulo());
        assertEquals("9780156012195", response.get(0).isbn());
        assertEquals("Uma história sobre amizade", response.get(0).descricao());
        assertEquals(1943, response.get(0).anoPublicacao());
        assertEquals("Antoine de Saint-Exupéri", response.get(0).autor());

        assertEquals("Dom Casmurro", response.get(1).titulo());

        verify(repository).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverLivros() {

        when(repository.findAll()).thenReturn(List.of());

        List<LivroResponseDTO> response = service.listar();

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(repository).findAll();
    }

    @Test
    void deveAlterarLivro() {

        Livro livroExistente = new Livro();
        livroExistente.setTitulo("Título Antigo");
        livroExistente.setIsbn("0000000000000");
        livroExistente.setDescricao("Descrição antiga");
        livroExistente.setAnoPublicacao(1900);
        livroExistente.setAutor("Autor Antigo");

        LivroRequestDTO request = new LivroRequestDTO(
                "O Pequeno Príncipe",
                "9780156012195",
                "Uma história sobre amizade",
                1943,
                "Antoine de Saint-Exupéri"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(livroExistente));
        when(repository.save(any(Livro.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LivroResponseDTO response = service.alterar(1L, request);

        assertNotNull(response);
        assertEquals("O Pequeno Príncipe", response.titulo());
        assertEquals("9780156012195", response.isbn());
        assertEquals("Uma história sobre amizade", response.descricao());
        assertEquals(1943, response.anoPublicacao());
        assertEquals("Antoine de Saint-Exupéri", response.autor());

        verify(repository).findById(1L);
        verify(repository).save(livroExistente);
    }

    @Test
    void deveLancarExcecaoAoAlterarLivroNaoEncontrado() {

        LivroRequestDTO request = new LivroRequestDTO(
                "O Pequeno Príncipe",
                "9780156012195",
                "Uma história sobre amizade",
                1943,
                "Antoine de Saint-Exupéri"
        );

        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.alterar(99L, request));

        assertEquals("Livro não encontrado.", exception.getMessage());

        verify(repository).findById(99L);
        verify(repository, never()).save(any(Livro.class));
    }

    @Test
    void deveDeletarLivro() {

        Livro livro = new Livro();
        livro.setTitulo("O Pequeno Príncipe");

        when(repository.findById(1L)).thenReturn(Optional.of(livro));

        service.deletar(1L);

        verify(repository).findById(1L);
        verify(repository).delete(livro);
    }

    @Test
    void deveLancarExcecaoAoDeletarLivroNaoEncontrado() {

        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.deletar(99L));

        assertEquals("Livro não encontrado.", exception.getMessage());

        verify(repository).findById(99L);
        verify(repository, never()).delete(any(Livro.class));
    }
}