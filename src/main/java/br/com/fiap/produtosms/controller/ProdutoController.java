package br.com.fiap.produtosms.controller;

import br.com.fiap.produtosms.dto.ProdutoDto;
import br.com.fiap.produtosms.model.Produto;
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

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", ProdutoDto.empty(UUID.randomUUID()));
        model.addAttribute("modoEdicao", false);
        return "form-produto";
    }

    @GetMapping("/detalhe/{id}")
    public String detalhe(@PathVariable UUID id, Model model) {
        try {
            Produto produto = produtoService.findById(id);
            model.addAttribute("produto", ProdutoDto.from(produto));
            model.addAttribute("modoEdicao", true);
            return "detalhe-produto";
        } catch (NoSuchElementException ex) {
            model.addAttribute("produto", ProdutoDto.empty(id));
            model.addAttribute("modoEdicao", false);
            return "form-produto";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable UUID id, Model model) {
        Produto produto = produtoService.findById(id);
        model.addAttribute("produto", ProdutoDto.from(produto));
        model.addAttribute("modoEdicao", true);
        return "form-produto";
    }

    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute("produto") ProdutoDto produtoDto,
            @RequestParam(name = "modoEdicao", defaultValue = "false") boolean modoEdicao,
            Model model
    ) {
        try {
            produtoService.saveOrUpdate(produtoDto.toEntity());
            return "redirect:/produtos";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("produto", produtoDto);
            model.addAttribute("modoEdicao", modoEdicao);
            model.addAttribute("erro", ex.getMessage());
            return "form-produto";
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable UUID id) {
        produtoService.deleteById(id);
        return "redirect:/produtos";
    }
}