package br.com.quickq.quickq_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtUtil(@Value("${JWT_SECRET:}") String secret, @Value("${jwt.expiration-ms}") long expirationMs) {
        if (secret == null || secret.isBlank()) {
            // Sem JWT_SECRET configurado: gera uma chave aleatória para não travar a inicialização,
            // mas isso invalida todos os tokens a cada reinício do serviço (ex: deploy no Render).
            // Configure a variável de ambiente JWT_SECRET no Render para evitar isso em produção.
            secret = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
            log.warn("JWT_SECRET não configurado. Gerando chave temporária - todos os tokens serão " +
                    "invalidados a cada reinício da aplicação. Defina a variável de ambiente JWT_SECRET no Render.");
        }
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            // HS256 exige no mínimo 256 bits (32 bytes); preenche se o segredo configurado for curto demais.
            byte[] padded = new byte[32];
            System.arraycopy(keyBytes, 0, padded, 0, Math.min(keyBytes.length, 32));
            keyBytes = padded;
        }
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = expirationMs;
    }

    public String gerarToken(String email, String nome, String perfil) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)
                .claim("nome", nome)
                .claim("perfil", perfil)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(secretKey)
                .compact();
    }

    public Claims validarToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
