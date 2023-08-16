package com.project.placementagency.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.placementagency.filter.JwtRequestFilter;
import com.project.placementagency.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    @Autowired
    private UserService service;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/user/add/*").permitAll()
                        .requestMatchers("user/forgetPassword").permitAll()
                        .requestMatchers("user/resetPassword/*").permitAll()
                        .requestMatchers("/user/sendOTP").permitAll()
                        .requestMatchers("/user/check/*").permitAll()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/admin/login").permitAll()
                        .requestMatchers("/employer/login").permitAll()
                        .anyRequest().authenticated());
                http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter,
        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(service);
		authenticationProvider.setPasswordEncoder(encoder);
		return authenticationProvider;
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
