package br.com.quickq.quickq_api.controllers;

import br.com.quickq.quickq_api.entities.Usuario;
import br.com.quickq.quickq_api.security.JwtUtil;
import br.com.quickq.quickq_api.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios") // O endereço que o login.js procura!
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/salvar")
    public Usuario salvarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.salvarUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais) {
        String email = credenciais.get("email");
        String senha = credenciais.get("senha");

        Usuario usuario = usuarioService.autenticar(email, senha);

        if (usuario != null) {
            String token = jwtUtil.gerarToken(usuario.getEmail(), usuario.getNome(), usuario.getPerfil());
            return ResponseEntity.ok(Map.of("token", token, "usuario", usuario));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }
}