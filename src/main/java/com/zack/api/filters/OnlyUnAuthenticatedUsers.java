package com.zack.api.filters;
import com.zack.api.util.responses.enums.GlobalResponses;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


import java.io.IOException;

@WebFilter(urlPatterns = {"/user/login", "/user/register"})

public class OnlyUnAuthenticatedUsers  implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("aqui estamos!");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

