package br.com.fiap.produtosms.utils;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Map;

public final class GitHubUserUtils {

    private GitHubUserUtils() {
    }

    public static String getUsername(OAuth2AuthenticationToken authentication) {
        if (authentication == null) return "Usuário";

        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();

        Object name = attributes.get("name");
        if (name != null && !name.toString().isBlank()) return name.toString();

        Object login = attributes.get("login");
        if (login != null) return login.toString();

        return "Usuário";
    }

    public static String getAvatar(OAuth2AuthenticationToken authentication) {
        if (authentication == null) return null;

        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        Object avatar = attributes.get("avatar_url");

        return avatar != null ? avatar.toString() : null;
    }
}