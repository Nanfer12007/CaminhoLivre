package br.com.caminholivre.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.caminholivre.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String gerarToken(Usuario usuario) {

        Date agora = new Date();
        Date expiracao = new Date(
            agora.getTime() + expiration
        );

        return Jwts.builder()
            .subject(usuario.getEmail())
            .claim("papel", usuario.getPapel().name())
            .issuedAt(agora)
            .expiration(expiracao)
            .signWith(getSigningKey())
            .compact();
    }

    public String extrairEmail(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}