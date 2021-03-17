package org.tinder.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        boolean loggedIn = isCookie(request, response);
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/notfound")) {
            chain.doFilter(request, response);
        } else if (requestURI.startsWith("/css") || requestURI.startsWith("/js") || requestURI.startsWith("/image")) {
            chain.doFilter(request, response);
        } else if (!loggedIn && (requestURI.startsWith("/login") || requestURI.startsWith("/sign_up"))) {
            chain.doFilter(request, res);
        } else if (!loggedIn && !(requestURI.startsWith("/login") && requestURI.startsWith("/sign_up"))) {
            response.sendRedirect("/notfound");
        } else if (loggedIn && (requestURI.startsWith("/login") || requestURI.startsWith("/sign_up"))) {
            response.sendRedirect("/users");
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }

    }

    private boolean isCookie(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) for (Cookie cookie : cookies) if (cookie.getName().equals("uid")) return true;
        return false;
    }
}
