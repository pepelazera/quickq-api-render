package br.com.quickq.quickq_api.repositories;

import br.com.quickq.quickq_api.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Diz ao Spring que esta é uma interface de repositório
public interface RepositorioDeCliente extends JpaRepository<Cliente, Long> {

    // JpaRepository<Cliente, Long> significa:
    // 1. "Este repositório é para a entidade Cliente"
    // 2. "O ID (Chave Primária) do Cliente é do tipo Long"

    // A MÁGICA:
    // Você automaticamente já tem:
    // - save(Cliente cliente)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    // - ... e muitos outros!

    // Futuramente, podemos adicionar buscas personalizadas, como:
    // List<Cliente> findByNomeContaining(String nome);
}