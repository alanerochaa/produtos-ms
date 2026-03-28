package br.com.fiap.produtosms.dto;


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
    public static ProdutoDto from(br.com.fiap.produtosms.model.Produto produto) {
        return new ProdutoDto(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getCategoria()
        );
    }

    public static List<ProdutoDto> from(List<br.com.fiap.produtosms.model.Produto> produtos) {
        return produtos.stream()
                .map(ProdutoDto::from)
                .toList();
    }

    public br.com.fiap.produtosms.model.Produto toEntity() {
        br.com.fiap.produtosms.model.Produto produto = new br.com.fiap.produtosms.model.Produto();
        produto.setId(this.id);
        produto.setNome(this.nome);
        produto.setDescricao(this.descricao);
        produto.setPreco(this.preco);
        produto.setCategoria(this.categoria);
        return produto;
    }

    public static ProdutoDto empty(UUID id) {
        return new ProdutoDto(
                id,
                "",
                "",
                BigDecimal.ZERO,
                ""
        );
    }
}