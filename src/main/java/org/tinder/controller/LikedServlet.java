package org.tinder.controller;

import freemarker.template.TemplateException;
import org.tinder.constant.TemplateEngine;
import org.tinder.entity.User;
import org.tinder.service.CookieService;
import org.tinder.service.LikedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LikedServlet extends HttpServlet {
    private final TemplateEngine templateEngine;
    private final LikedService likedService;
    private final CookieService cookieService;

    public LikedServlet(TemplateEngine templateEngine, LikedService likedService, CookieService cookieService) {
        this.templateEngine = templateEngine;
        this.likedService = likedService;
        this.cookieService = cookieService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = cookieService.getUserIdWithCookie(req.getCookies());
        if (!userId.isEmpty()) {
            HashMap<String, Object> data = new HashMap<>();
            List<User> likedUsersById = likedService.getLikedUsersById(userId);
            data.put("users", likedUsersById);
            try {
                templateEngine.render("liked.ftl", data, resp);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("logout") != null) {
            cookieService.deleteCookie(req.getCookies(), resp);
            resp.sendRedirect("/login");
        }
    }

}
