package br.com.caminholivre.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caminholivre.dto.request.EstabelecimentoRequestDTO;
import br.com.caminholivre.dto.response.EstabelecimentoResponseDTO;
import br.com.caminholivre.service.EstabelecimentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estabelecimentos")
public class EstabelecimentoController {

    private final EstabelecimentoService service;

    public EstabelecimentoController(EstabelecimentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EstabelecimentoResponseDTO> criar(
            @Valid @RequestBody EstabelecimentoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<Page<EstabelecimentoResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "nome")
            Pageable pageable) {

        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoResponseDTO> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstabelecimentoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstabelecimentoRequestDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
