package com.project.placementagency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/user/add/*").permitAll()
                        .requestMatchers("/user/sendOTP").permitAll()
                        .requestMatchers("/user/check/*").permitAll()
                        .requestMatchers("/user/get").permitAll()
                        .requestMatchers("/admin/login").permitAll()
                        .requestMatchers("/employer/login").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // http.addFilterBefore(authenticationJwtTokenFilter,
        // UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    // http.csrf().disable().authorizeHttpRequests().antMatchers("/person/authenticate")
    // .permitAll().anyRequest().authenticated().and().sessionManagement()
    // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // http.addFilterBefore(jwtRequestFilter,
    // UsernamePasswordAuthenticationFilter.class);
    // }
}
