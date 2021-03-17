package org.tinder.controller;

import freemarker.template.TemplateException;
import org.tinder.constant.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class ChatServlet extends HttpServlet {
    private final TemplateEngine templateEngine;

    public ChatServlet(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            templateEngine.render("chat.ftl", new HashMap<>(), resp);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

}
