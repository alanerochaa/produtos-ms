package br.com.fiap.produtosms.dto;

import br.com.fiap.produtosms.entities.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProdutoDto(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        String categoria
) {

    public static ProdutoDto from(Produto produto) {
        return new ProdutoDto(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getCategoria()
        );
    }

    public static List<ProdutoDto> from(List<Produto> produtos) {
        return produtos.stream()
                .map(ProdutoDto::from)
                .toList();
    }

    public Produto toEntity() {
        return new Produto(id, nome, descricao, preco, categoria);
    }

    public static ProdutoDto empty(UUID id) {
        return new ProdutoDto(id, "", "", null, "");
    }
}