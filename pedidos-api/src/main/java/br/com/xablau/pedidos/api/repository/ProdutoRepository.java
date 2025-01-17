package br.com.xablau.pedidos.api.repository;

import br.com.xablau.pedidos.api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    boolean existsByNome(String nome);
}
