package br.com.fiap.produtosms.service;

import br.com.fiap.produtosms.entities.Produto;
import br.com.fiap.produtosms.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto findById(UUID id) {
        return produtoRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    @Transactional
    public void saveOrUpdate(Produto produto) {
        produtoRepository.save(produto);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        produtoRepository.deleteById(id);
    }
}