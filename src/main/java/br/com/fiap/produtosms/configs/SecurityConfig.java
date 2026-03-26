package br.com.fiap.produtosms.configs;

import br.com.fiap.produtosms.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] PUBLIC_ROUTES = {
            "/",
            "/403",
            "/css/**",
            "/js/**",
            "/h2-console/**"
    };

    private static final String[] PRODUTO_WRITE_ROUTES = {
            "/produtos/novo",
            "/produtos/detalhe/**",
            "/produtos/salvar",
            "/produtos/excluir/**"
    };

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_ROUTES).permitAll()
                        .requestMatchers("/produtos").authenticated()
                        .requestMatchers(PRODUTO_WRITE_ROUTES).hasRole("PRODUTO")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                )
                .exceptionHandling(exception ->
                        exception.accessDeniedPage("/403")
                )
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers ->
                        headers.frameOptions(frame -> frame.disable())
                );

        return http.build();
    }
}