package br.com.quickq.quickq_api.services;

import br.com.quickq.quickq_api.entities.Usuario;
import br.com.quickq.quickq_api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UsuarioService {

    private static final Pattern BCRYPT_PATTERN = Pattern.compile("^\\$2[aby]\\$.*");

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvarUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setCreatedAt(LocalDateTime.now());
            usuario.setActive(true); // Novos usuários nascem ativos
        }
        if (!isBcrypt(usuario.getHashSenha())) {
            usuario.setHashSenha(passwordEncoder.encode(usuario.getHashSenha()));
        }
        usuario.setUpdatedAt(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return null;
        }

        Usuario u = usuarioOpt.get();
        if (!u.isActive()) {
            return null;
        }

        String senhaSalva = u.getHashSenha();
        if (isBcrypt(senhaSalva)) {
            return passwordEncoder.matches(senha, senhaSalva) ? u : null;
        }

        // Conta antiga com senha em texto puro: valida e migra para BCrypt silenciosamente.
        if (senhaSalva.equals(senha)) {
            u.setHashSenha(passwordEncoder.encode(senha));
            usuarioRepository.save(u);
            return u;
        }
        return null;
    }

    private boolean isBcrypt(String valor) {
        return valor != null && BCRYPT_PATTERN.matcher(valor).matches();
    }
}