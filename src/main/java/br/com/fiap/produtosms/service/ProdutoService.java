package br.com.fiap.produtosms.service;

import br.com.fiap.produtosms.model.Produto;

import java.util.List;
import java.util.UUID;

public interface ProdutoService {

    List<Produto> findAll();

    Produto findById(UUID id);

    Produto saveOrUpdate(Produto produto);

    void deleteById(UUID id);
}