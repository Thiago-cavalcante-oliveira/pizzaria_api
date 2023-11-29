package com.example.pizzaria.config;


import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/*").permitAll()
                        .requestMatchers("/assets/*").permitAll()
                        .requestMatchers("api/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/login/create").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/cliente").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "api/cliente").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/cliente").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/cliente/*").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/pedido").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "api/pedido").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/pedido").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/pedido/*").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/endereco").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "api/endereco").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/endereco").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/endereco/*").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/pizza").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "api/pizza").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/pizza").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/pizza/*").permitAll()



                        .requestMatchers(HttpMethod.POST,"api/sabor" ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"api/sabor" ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/sabor" ).hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"api/produto_diverso" ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"api/produto_diverso" ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/produto_diverso" ).hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"api/pizza_tipo" ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"api/pizza_tipo" ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/pizza_tipo" ).hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"api/funcionario" ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"api/funcionario" ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/funcionario" ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"api/funcionario/*" ).hasAuthority("ADMIN")

                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION,HttpHeaders.CONTENT_TYPE,HttpHeaders.ACCEPT));
        config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.DELETE.name()));
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
        bean.setOrder(-102);
        return bean;
    }
}
