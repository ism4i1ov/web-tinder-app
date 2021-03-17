package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.omg.SendingContext.RunTime;
import org.tinder.controller.*;
import org.tinder.constant.TemplateEngine;
import org.tinder.dao.impl.LikeDao;
import org.tinder.dao.impl.UserDao;
import org.tinder.filter.AuthFilter;
import org.tinder.service.*;

import javax.servlet.DispatcherType;
import java.security.spec.ECField;
import java.util.EnumSet;

public class Main {

    public static void main(String[] args) throws Exception {

        final Server server = new Server(80);
        final TemplateEngine templateEngine = TemplateEngine.folder("templates");
        final ServletContextHandler handler = new ServletContextHandler();

        final EncodingService encodingService = new EncodingService();
        final CookieService cookieService = new CookieService(encodingService);
        final UserDao userDao = new UserDao();
        final SignUpService signUpService = new SignUpService(userDao);
        final SignInService signInService = new SignInService(userDao);
        final LikeDao likeDao = new LikeDao();
        final LikedService likedService = new LikedService(likeDao);
        final LikeService likeService = new LikeService(likeDao);

        handler.addServlet(new ServletHolder(new LoginServlet(templateEngine, signInService, encodingService)), "/login");
        handler.addServlet(new ServletHolder(new SignUpServlet(templateEngine, signUpService, encodingService)), "/sign_up");
        handler.addServlet(new ServletHolder(new ChatServlet(templateEngine)), "/messages");
        handler.addServlet(new ServletHolder(new LikedServlet(templateEngine, likedService, cookieService)), "/liked");
        handler.addServlet(new ServletHolder(new LikeServlet(templateEngine, likeService, cookieService)), "/users");
        handler.addServlet(NotFoundServlet.class, "/notfound");
        handler.addServlet(new ServletHolder(new StaticServlet("css")), "/css/*");
        handler.addServlet(new ServletHolder(new StaticServlet("js")), "/js/*");
        handler.addServlet(new ServletHolder(new StaticServlet("image")), "/image/*");

        handler.addFilter(AuthFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
