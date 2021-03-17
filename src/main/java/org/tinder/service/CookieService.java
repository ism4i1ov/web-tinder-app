package org.tinder.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class CookieService {

    private final EncodingService encodingService;

    public CookieService(EncodingService encodingService) {
        this.encodingService = encodingService;
    }


    public String getUserIdWithCookie(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("uid"))
                .findFirst()
                .map(cookie -> encodingService.decoding(cookie.getValue()))
                .orElse("");
    }

    public void deleteCookie(Cookie[] cookies, HttpServletResponse resp) {
        Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("uid"))
                .findFirst()
                .map(cookie -> {
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                    return 1;
                });
    }

}
