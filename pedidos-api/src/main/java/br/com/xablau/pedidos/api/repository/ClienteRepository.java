package br.com.xablau.pedidos.api.repository;

import br.com.xablau.pedidos.api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    boolean existsByEmail(String email);
}
