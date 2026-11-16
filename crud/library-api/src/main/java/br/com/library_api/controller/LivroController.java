package br.com.library_api.controller;

import br.com.library_api.dto.request.LivroRequestDTO;
import br.com.library_api.dto.response.LivroResponseDTO;
import br.com.library_api.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> cadastrar(@Valid @RequestBody LivroRequestDTO livroRequest) {
        LivroResponseDTO livroSalvo = service.cadastrar(livroRequest);
        URI location = URI.create("/livros/" + livroSalvo.id());
        return ResponseEntity.created(location).body(livroSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable Long id) {
        LivroResponseDTO livro = service.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listar() {
        List<LivroResponseDTO> livros = service.listar();
        return ResponseEntity.ok(livros);
    }
}