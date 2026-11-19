package br.com.caminholivre.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caminholivre.dto.request.LoginRequestDTO;
import br.com.caminholivre.dto.request.UsuarioRequestDTO;
import br.com.caminholivre.dto.response.AuthResponseDTO;
import br.com.caminholivre.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<AuthResponseDTO> cadastrar(
            @Valid @RequestBody UsuarioRequestDTO dto) {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authService.cadastrar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(authService.login(dto));
    }
}