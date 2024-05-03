package com.example.Login.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter ;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    @Value("${jwt.signerKey}")
    private String signerKey;
    private final String[] PUBLIC_ENDPOINTS_POST_ALL = {"/users",
            "/auth/token", "/auth/introspect"
    };
    private final String[] PUBLIC_ENDPOINTS_GET_ALL = {"/posts/{postId}", "/posts"
    };
    private final String[] PUBLIC_ENDPOINTS_GET_ADMIN = {"/users"
    };

    private final String[] PUBLIC_ENDPOINTS_POST_ADMIN = {"/posts"
    };
    private final String[] PUBLIC_ENDPOINTS_DELETE_ADMIN = {"/users/{username}"};

    private final String[] PUBLIC_ENDPOINTS_PUT_ADMIN = {"/posts/{postId}"};
    private final String[] PUBLIC_ENDPOINTS_LOCAL = {"/users/login_user", "/checklogin"};


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS_POST_ALL).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS_LOCAL).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS_GET_ALL).permitAll()
                        .requestMatchers(HttpMethod.PUT, PUBLIC_ENDPOINTS_POST_ADMIN).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINTS_GET_ADMIN).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, PUBLIC_ENDPOINTS_DELETE_ADMIN).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, PUBLIC_ENDPOINTS_PUT_ADMIN).hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated());


        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer ->
                                jwtConfigurer.decoder(jwtDecoder())
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}