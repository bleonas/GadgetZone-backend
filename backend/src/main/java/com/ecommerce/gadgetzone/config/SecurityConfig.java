package com.ecommerce.gadgetzone.config;

import com.ecommerce.gadgetzone.config.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers(headers -> headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable))
                .httpBasic(Customizer.withDefaults())
                .userDetailsService(userDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,
                                "/user/signup",
                                "/user/login", 
                                "/user/logout",
                                "/admin/create-admin",
                                "/admin/brand",
                                "/admin/category",
                                "/admin/warehouse",
                                "/admin/add-product",
                                "/cart/add-to-cart",
                                "/payment/add",
                                "/orders/{userId}").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/user/profile",
                                "/admin/brand",
                                "/admin/category",
                                "/admin/users",
                                "/admin/add-products/{productId}",
                                "/admin/products-list",
                                "/products",
                                "/src/main/resources/static/images/**",
                                "/orders/{userId}",
                                "/orders/pay/{orderId}",
                                "/cart/{userId}",
                                "/admin/orders").permitAll()
                        .requestMatchers(HttpMethod.DELETE,
                                "/admin/users/{userId}",
                                "/admin/products-list/{productId}",
                                "/orders/{userId}/{orderId}",
                                "/cart/{userId}/{productId}").permitAll()
                        .requestMatchers(HttpMethod.PUT,
                                "/admin/edit-product/{productId}",
                                "/admin/add-product-amount/{productId}",
                                "/cart/{userId}").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/swagger-resources").permitAll()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}