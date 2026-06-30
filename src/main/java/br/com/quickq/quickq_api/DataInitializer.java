package br.com.quickq.quickq_api;

import br.com.quickq.quickq_api.entities.Usuario;
import br.com.quickq.quickq_api.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.admin.initial-email:admin@quickq.com}")
    private String initialAdminEmail;

    @Value("${app.admin.initial-password:}")
    private String initialAdminPassword;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe algum usuário. Se não, cria o Admin.
        if (usuarioRepository.count() == 0) {
            String senha = initialAdminPassword;
            boolean senhaGerada = senha == null || senha.isBlank();
            if (senhaGerada) {
                // Sem ADMIN_INITIAL_PASSWORD configurada: gera uma senha aleatória, mostrada
                // apenas uma vez no log de boot. Troque-a assim que possível.
                senha = UUID.randomUUID().toString();
            }

            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail(initialAdminEmail);
            admin.setHashSenha(passwordEncoder.encode(senha));
            admin.setPerfil("ADMIN");
            admin.setActive(true);
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());

            usuarioRepository.save(admin);

            log.info("--- USUÁRIO ADMIN CRIADO COM SUCESSO ---");
            log.info("Email: {}", initialAdminEmail);
            if (senhaGerada) {
                log.info("Senha (gerada automaticamente, troque após o primeiro login): {}", senha);
            } else {
                log.info("Senha: definida via ADMIN_INITIAL_PASSWORD");
            }
            log.info("----------------------------------------");
        }
    }
}