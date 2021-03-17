package org.tinder.controller;

import freemarker.template.TemplateException;
import org.tinder.constant.TemplateEngine;
import org.tinder.entity.Like;
import org.tinder.entity.User;
import org.tinder.service.CookieService;
import org.tinder.service.LikeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;


public class LikeServlet extends HttpServlet {
    private final TemplateEngine templateEngine;
    private final LikeService likeService;
    private final CookieService cookieService;

    public LikeServlet(TemplateEngine templateEngine, LikeService likeService, CookieService cookieService) {
        this.templateEngine = templateEngine;
        this.likeService = likeService;
        this.cookieService = cookieService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = cookieService.getUserIdWithCookie(req.getCookies());
        if (!userId.isEmpty()) {
            try {
                HashMap<String, Object> data = new HashMap<>();
                Optional<User> user = likeService.getUserForLike(userId);
                if (user.isPresent()) {
                    data.put("user", user.get());
                    templateEngine.render("like.ftl", data, resp);
                } else {
                    resp.sendRedirect("/liked");
                }
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("like") != null) {
            String likedUserId = req.getParameterValues("like")[0];
            String likerUserId = cookieService.getUserIdWithCookie(req.getCookies());
            Like like = new Like(0, Long.parseLong(likedUserId), true);
            likeService.likedUser(like, likerUserId);
            resp.sendRedirect("/users");
        } else if (req.getParameter("dislike") != null) {
            String likedUserId = req.getParameterValues("dislike")[0];
            String likerUserId = cookieService.getUserIdWithCookie(req.getCookies());
            Like like = new Like(0, Long.parseLong(likedUserId), false);
            likeService.likedUser(like, likerUserId);
            resp.sendRedirect("/users");
        }
    }
}
