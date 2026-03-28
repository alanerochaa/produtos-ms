package br.com.fiap.produtosms.service;

import br.com.fiap.produtosms.entities.Usuario;
import br.com.fiap.produtosms.repositories.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsuarioRepository usuarioRepository;

    public CustomOAuth2UserService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String login = oauth2User.getAttribute("login");
        String nome = oauth2User.getAttribute("name");
        String avatarUrl = oauth2User.getAttribute("avatar_url");

        if (login == null || login.isBlank()) {
            throw new OAuth2AuthenticationException("Login do usuário no GitHub não foi retornado.");
        }

        if (nome == null || nome.isBlank()) {
            nome = login;
        }

        String finalNome = nome;

        Usuario usuario = usuarioRepository.findById(login)
                .map(usuarioExistente -> {
                    usuarioExistente.setNome(finalNome);
                    usuarioExistente.setAvatarUrl(avatarUrl);

                    if (usuarioExistente.getRole() == null || usuarioExistente.getRole().isBlank()) {
                        usuarioExistente.setRole("ROLE_USER");
                    }

                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseGet(() -> usuarioRepository.save(
                        new Usuario(login, finalNome, avatarUrl, "ROLE_USER")
                ));

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority(usuario.getRole())),
                oauth2User.getAttributes(),
                "login"
        );
    }
}