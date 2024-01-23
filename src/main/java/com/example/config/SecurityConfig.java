package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    //configuration 1
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/v1/index2").permitAll()//la ruta que no va a necesitar autorizacion
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    //configuration 2
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(auth ->{
//                    auth.requestMatchers("/v1/index2").permitAll();
//                    auth.anyRequest().authenticated();
//                })
//                .formLogin()
//                    .successHandler(successHandler()) //URL a donde se va a ir despues  de iniciar sesion
//                    .permitAll()
//                .and()
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                    .invalidSessionUrl("/logins") //enviar al login cuando la sesion haya sido erronea al momento de iniciar sesion
//                    .maximumSessions(1)
//                    .expiredUrl("/login")
//                    .sessionRegistry(sessionRegistry())
//                .and()
//                .sessionFixation()
//                    .migrateSession()//migrateSession - newSession
//                .and()
//                .build();
//    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    public AuthenticationSuccessHandler successHandler(){
        return ((request,response,authentication)->{
            response.sendRedirect("/v1/session");
        });
    }

}
