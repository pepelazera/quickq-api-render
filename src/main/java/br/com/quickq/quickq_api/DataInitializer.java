package br.com.quickq.quickq_api;

import br.com.quickq.quickq_api.entities.Usuario;
import br.com.quickq.quickq_api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe algum usuário. Se não, cria o Admin.
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@quickq.com");
            admin.setHashSenha("123456"); // Senha simples para teste
            admin.setPerfil("ADMIN");
            admin.setActive(true);
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());

            usuarioRepository.save(admin);
            System.out.println("--- USUÁRIO ADMIN CRIADO COM SUCESSO ---");
            System.out.println("Email: admin@quickq.com");
            System.out.println("Senha: 123456");
            System.out.println("----------------------------------------");
        }
    }
}