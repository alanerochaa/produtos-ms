package br.com.fiap.produtosms.controller;

import br.com.fiap.produtosms.utils.GitHubUserUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class CommonController {

    @ModelAttribute
    public void preProcessamento(Model model, Authentication authentication) {
        boolean autenticado = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        if (autenticado) {
            if (authentication instanceof OAuth2AuthenticationToken oauth2Auth) {
                model.addAttribute("username", GitHubUserUtils.getUsername(oauth2Auth));
                model.addAttribute("urlAvatar", GitHubUserUtils.getAvatar(oauth2Auth));
            } else {
                model.addAttribute("username", authentication.getName());
                model.addAttribute("urlAvatar", null);
            }

            model.addAttribute("autenticado", true);
        } else {
            model.addAttribute("username", "Visitante");
            model.addAttribute("urlAvatar", null);
            model.addAttribute("autenticado", false);
        }
    }
}