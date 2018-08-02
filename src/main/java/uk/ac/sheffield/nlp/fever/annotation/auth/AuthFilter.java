package uk.ac.sheffield.nlp.fever.annotation.auth;


import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


public class AuthFilter implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Long user = (Long) req.getSession().getAttribute("annotator");

        if (user == null) {

            SecurityContextHolder.getContext().setAuthentication(new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null;
                }

                @Override
                public Object getCredentials() {
                    return null;
                }

                @Override
                public Object getDetails() {
                    return null;
                }

                @Override
                public Object getPrincipal() {
                    return null;
                }

                @Override
                public boolean isAuthenticated() {
                    return false;
                }

                @Override
                public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                }

                @Override
                public String getName() {
                    return null;
                }
            });

            res.sendRedirect("/login");
            return;

        } else {
            SecurityContextHolder.getContext().setAuthentication(new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null;
                }

                @Override
                public Object getCredentials() {
                    return user;
                }

                @Override
                public Object getDetails() {
                    return user;
                }

                @Override
                public Object getPrincipal() {
                    return user;
                }

                @Override
                public boolean isAuthenticated() {
                    return true;
                }

                @Override
                public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                }

                @Override
                public String getName() {
                    return Long.toString(user);
                }
            });
        }
        next.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
