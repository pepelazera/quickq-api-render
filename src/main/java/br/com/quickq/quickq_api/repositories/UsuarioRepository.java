package br.com.quickq.quickq_api.repositories;

import br.com.quickq.quickq_api.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca mágica para o login
    Optional<Usuario> findByEmail(String email);
}