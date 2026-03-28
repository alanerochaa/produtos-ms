package br.com.fiap.produtosms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<br.com.fiap.produtosms.model.Produto, UUID> {
}