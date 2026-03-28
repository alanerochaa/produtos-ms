package br.com.fiap.produtosms.service;

import br.com.fiap.produtosms.model.Produto;
import br.com.fiap.produtosms.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto findById(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado: " + id));
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto saveOrUpdate(Produto produto) {
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    @Override
    public void deleteById(UUID id) {
        produtoRepository.deleteById(id);
    }

    private void validarProduto(Produto produto) {

        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }

        if (produto.getId() == null) {
            throw new IllegalArgumentException("Código do produto é obrigatório.");
        }

        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }

        if (produto.getCategoria() == null || produto.getCategoria().isBlank()) {
            throw new IllegalArgumentException("Categoria do produto é obrigatória.");
        }

        if (produto.getPreco() == null) {
            throw new IllegalArgumentException("Preço do produto é obrigatório.");
        }

        if (produto.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo.");
        }
    }
}