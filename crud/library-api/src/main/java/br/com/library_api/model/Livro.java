package br.com.library_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "livros")
@NoArgsConstructor
@Getter
@Setter
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    @Column(length = 2000)
    private String descricao;

    @Column(name = "ano_publicacao", nullable = false)
    private Integer anoPublicacao;

    @Column(nullable = false, length = 255)
    private String autor;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private Instant dataCadastro;

    @Column(name = "data_alteracao")
    private Instant dataAlteracao;

    @PrePersist
    void aoCriar() {
        Instant agora = Instant.now();
        this.dataCadastro = agora;
        this.dataAlteracao = agora;
    }

    @PreUpdate
    void aoAlterar() {
        this.dataAlteracao = Instant.now();
    }

}