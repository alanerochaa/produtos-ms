package br.com.fiap.produtosms.configs;

import br.com.fiap.produtosms.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/produtos/novo").hasRole("PRODUTO")
                        .requestMatchers("/produtos/salvar").hasRole("PRODUTO")
                        .requestMatchers("/produtos/excluir/**").hasRole("PRODUTO")
                        .requestMatchers("/produtos/detalhe/**").hasRole("PRODUTO")
                        .requestMatchers("/produtos").authenticated()
                        .requestMatchers("/", "/403", "/css/**", "/js/**", "/h2-console/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/403"));

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}