package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.Usuario;
import br.com.quickq.quickq_api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setCreatedAt(LocalDateTime.now());
            usuario.setActive(true); // Novos usuários nascem ativos
        }
        usuario.setUpdatedAt(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            // Compara a senha (em texto puro para este protótipo)
            if (u.getHashSenha().equals(senha) && u.isActive()) {
                return u;
            }
        }
        return null;
    }
}