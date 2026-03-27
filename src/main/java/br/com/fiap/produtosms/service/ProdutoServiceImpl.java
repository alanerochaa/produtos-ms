package br.com.fiap.produtosms.service;

import br.com.fiap.produtosms.model.Produto;
import br.com.fiap.produtosms.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto findById(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
    }

    @Override
    public Produto saveOrUpdate(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public void deleteById(UUID id) {
        produtoRepository.deleteById(id);
    }
}