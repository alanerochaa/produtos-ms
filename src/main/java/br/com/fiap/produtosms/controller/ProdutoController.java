package br.com.fiap.produtosms.controller;

import br.com.fiap.produtosms.dto.ProdutoDto;
import br.com.fiap.produtosms.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequestMapping("/produtos")
public class ProdutoController extends CommonController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", ProdutoDto.from(produtoService.findAll()));
        return "produtos";
    }

    @GetMapping("/detalhe/{id}")
    public String detalhe(@PathVariable UUID id, Model model) {
        ProdutoDto produtoDto;

        try {
            produtoDto = ProdutoDto.from(produtoService.findById(id));
        } catch (NoSuchElementException e) {
            produtoDto = ProdutoDto.empty(id);
        }

        model.addAttribute("produto", produtoDto);
        return "detalhe-produto";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", ProdutoDto.empty(UUID.randomUUID()));
        return "detalhe-produto";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("produto") ProdutoDto produtoDto) {
        produtoService.saveOrUpdate(produtoDto.toEntity());
        return "redirect:/produtos";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable UUID id) {
        produtoService.deleteById(id);
        return "redirect:/produtos";
    }
}