package uk.ac.sheffield.nlp.fever.annotation.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;


/*
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http
                .authorizeRequests()

                .antMatchers(

                        "/",
                        "/register",
                        "/register/**",
                        "/login",
                        "/login/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .logout().permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        http.addFilterBefore(new AuthFilter(), SecurityContextPersistenceFilter.class);


    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return authentication;
            }
        };
    }


}
*/


