package br.com.caminholivre.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.caminholivre.dto.request.LoginRequestDTO;
import br.com.caminholivre.dto.request.UsuarioRequestDTO;
import br.com.caminholivre.dto.response.AuthResponseDTO;
import br.com.caminholivre.entity.Usuario;
import br.com.caminholivre.enums.Papel;
import br.com.caminholivre.repository.UsuarioRepository;
import br.com.caminholivre.security.JwtService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponseDTO cadastrar(UsuarioRequestDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException(
                "Este e-mail já está cadastrado"
            );
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        // Cadastro público nunca pode criar um gestor.
        usuario.setPapel(Papel.CIDADAO);

        usuario = usuarioRepository.save(usuario);

        String token = jwtService.gerarToken(usuario);

        return montarResposta(usuario, token);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.email(),
                dto.senha()
            )
        );

        Usuario usuario = usuarioRepository
            .findByEmail(dto.email())
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "E-mail ou senha inválidos"
                )
            );

        String token = jwtService.gerarToken(usuario);

        return montarResposta(usuario, token);
    }

    private AuthResponseDTO montarResposta(
            Usuario usuario,
            String token) {

        return new AuthResponseDTO(
            token,
            "Bearer",
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPapel().name()
        );
    }
}