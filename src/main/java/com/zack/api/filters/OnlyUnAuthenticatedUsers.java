package com.zack.api.filters;

import com.zack.api.util.responses.enums.GlobalResponses;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;

import java.io.IOException;

@WebFilter
public class OnlyUnAuthenticatedUsers implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isLogged(servletRequest)) {
            throw new BadRequestException(GlobalResponses.USER_IS_AUTHENTICATED.getText());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    private boolean isLogged(ServletRequest request) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String header = httpRequest.getHeader("Authorization");
        return header!=null;
    }
}

