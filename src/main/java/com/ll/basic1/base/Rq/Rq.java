package com.ll.basic1.base.Rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class Rq {
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public Rq(HttpServletRequest req){
        this.req = req;
    }

    public Rq(HttpServletResponse resp){
        this.resp = resp;
    }

    public void setCookie(String id, Object value){
        resp.addCookie(new Cookie(id, (String)value));
    }

    public String getCookie(String id, String defaultValue){
        String value = null;

        if(req.getCookies() != null){
            value = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(id))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);

        }

        if(value == null) return defaultValue;

        return value;
    }

    public long getCookieAsLong(String id, long defaultValue){
        long value = -1;

        if(req.getCookies() != null){
            value = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(id))
                    .map(Cookie::getValue)
                    .mapToLong(Long::parseLong)
                    .findFirst()
                    .orElse(-1);
        }

        if(value == -1) return defaultValue;

        return value;
    }

    public void removeCookie(String id) {
        if(req.getCookies() != null){
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(id))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
        }
    }
}
