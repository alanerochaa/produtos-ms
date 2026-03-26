package br.com.fiap.produtosms.controller;

import br.com.fiap.produtosms.utils.GitHubUserUtils;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class CommonController {

    @ModelAttribute
    public void preProcessamento(Model model, OAuth2AuthenticationToken authentication) {
        if (authentication != null) {
            model.addAttribute("username", GitHubUserUtils.getUsername(authentication));
            model.addAttribute("urlAvatar", GitHubUserUtils.getAvatar(authentication));
        } else {
            model.addAttribute("username", "Usuário");
            model.addAttribute("urlAvatar", null);
        }
    }
}