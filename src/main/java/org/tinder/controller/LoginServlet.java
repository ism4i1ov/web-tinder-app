package org.tinder.controller;

import freemarker.template.TemplateException;
import org.tinder.constant.TemplateEngine;
import org.tinder.service.EncodingService;
import org.tinder.service.SignInService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {

    private final TemplateEngine templateEngine;
    private final SignInService signInService;
    private final EncodingService encodingService;

    public LoginServlet(TemplateEngine templateEngine, SignInService signInService, EncodingService encodingService) {
        this.templateEngine = templateEngine;
        this.signInService = signInService;
        this.encodingService = encodingService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("sign_in") != null) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String encodePassword = encodingService.encoding(password);
            Boolean login = signInService.getUserByUsernameAndPassword(username, encodePassword)
                    .map(user -> {
                        Cookie cookie = new Cookie("uid", encodingService.encoding(String.valueOf(user.getId())));
                        resp.addCookie(cookie);
                        signInService.updateLastLogin(String.valueOf(user.getId()), LocalDateTime.now());
                        return true;
                    }).orElse(false);
            if (login) resp.sendRedirect("/users");
            else try (PrintWriter pw = resp.getWriter()) {
                pw.write("Username or password is incorrect");
            }
        } else resp.sendRedirect("/sign_up");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            templateEngine.render("login.ftl", new HashMap<>(), resp);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
