package br.com.fiap.produtosms.service;


import java.util.List;
import java.util.UUID;

public interface ProdutoService {

    br.com.fiap.produtosms.model.Produto findById(UUID id);

    List<br.com.fiap.produtosms.model.Produto> findAll();

    br.com.fiap.produtosms.model.Produto saveOrUpdate(br.com.fiap.produtosms.model.Produto produto);

    void deleteById(UUID id);
}