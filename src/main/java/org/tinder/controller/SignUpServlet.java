package org.tinder.controller;

import freemarker.template.TemplateException;
import org.tinder.constant.TemplateEngine;
import org.tinder.entity.User;
import org.tinder.service.EncodingService;
import org.tinder.service.SignUpService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class SignUpServlet extends HttpServlet {

    private final TemplateEngine templateEngine;
    private final SignUpService signUpService;
    private final EncodingService encodingService;

    public SignUpServlet(TemplateEngine templateEngine, SignUpService signUpService, EncodingService encodingService) {
        this.templateEngine = templateEngine;
        this.signUpService = signUpService;
        this.encodingService = encodingService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            templateEngine.render("sign_up.ftl", new HashMap<>(), resp);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String username = req.getParameter("username");
        String avatar_link = req.getParameter("avatar_link");
        String profession = req.getParameter("profession");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm_password");
        String encodePassword = encodingService.encoding(password);
        if (password.equals(confirmPassword)) {
            if (!signUpService.findByUsername(username).isPresent()) {
                signUpService.createNewUser(new User(-1, name, surname, username, encodePassword, avatar_link, profession));
                resp.sendRedirect("/login");
            } else {
                try (PrintWriter pw = resp.getWriter()) {
                    pw.write("Username is exists!");
                }
            }
        } else {
            try (PrintWriter pw = resp.getWriter()) {
                pw.write("Confirm password is incorrect!");
            }
        }
    }
}
