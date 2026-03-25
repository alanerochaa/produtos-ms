package br.com.fiap.produtosms.service;

import br.com.fiap.produtosms.entities.Usuario;
import br.com.fiap.produtosms.repositories.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsuarioRepository usuarioRepository;

    public CustomOAuth2UserService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String login = oauth2User.getAttribute("login");
        String nome = oauth2User.getAttribute("name");
        String avatarUrl = oauth2User.getAttribute("avatar_url");

        Usuario usuario = usuarioRepository.findById(login)
                .orElseGet(() -> usuarioRepository.save(
                        new Usuario(login, nome, avatarUrl, "ROLE_PRODUTO")
                ));

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority(usuario.getRole())),
                oauth2User.getAttributes(),
                "login"
        );
    }
}