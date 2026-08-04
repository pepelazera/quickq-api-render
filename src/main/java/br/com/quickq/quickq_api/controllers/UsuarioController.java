package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.dto.CadastroUsuarioDTO;
import br.com.quickq.quickq_api.entities.Usuario;
import br.com.quickq.quickq_api.security.CookieUtil;
import br.com.quickq.quickq_api.security.JwtUtil;
import br.com.quickq.quickq_api.services.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios") // O endereço que o login.js procura!
@CrossOrigin(origins = "*")
public class UsuarioController {

    // /salvar é público (autocadastro). ADMIN não entra aqui de propósito: só pode ser
    // promovido por um admin já autenticado (fluxo ainda não implementado).
    private static final Set<String> PERFIS_AUTOCADASTRO = Set.of("MEDICO", "NEUROPSICOLOGO", "OPERADOR", "PACIENTE");

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping("/salvar")
    public ResponseEntity<?> salvarUsuario(@RequestBody CadastroUsuarioDTO dados) {
        String perfil = dados.perfil() == null ? "" : dados.perfil().trim().toUpperCase();
        if (!PERFIS_AUTOCADASTRO.contains(perfil)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Perfil inválido para autocadastro.");
        }

        // Usuario é construído do zero aqui: id, active, createdAt etc. nunca vêm do
        // cliente, então não dá pra sobrescrever um usuário existente via esse endpoint.
        Usuario usuario = new Usuario(dados.nome(), dados.email(), dados.hashSenha(), perfil, true);
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais, HttpServletResponse response) {
        String email = credenciais.get("email");
        String senha = credenciais.get("senha");

        Usuario usuario = usuarioService.autenticar(email, senha);

        if (usuario != null) {
            String token = jwtUtil.gerarToken(usuario.getEmail(), usuario.getNome(), usuario.getPerfil());
            String csrfToken = UUID.randomUUID().toString();

            response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.buildAuthCookie(token).toString());
            response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.buildCsrfCookie(csrfToken).toString());

            return ResponseEntity.ok(Map.of("usuario", usuario));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.clearAuthCookie().toString());
        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.clearCsrfCookie().toString());
        return ResponseEntity.ok().build();
    }
}