package com.ll.basic1.base.rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;

@Component
@RequestScope
@AllArgsConstructor
public class Rq {
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public void setCookie(String key, Object value){
        resp.addCookie(new Cookie(key, (String)value));
    }

    public String getCookie(String key, String defaultValue){
        String value = null;

        if(req.getCookies() != null){
            value = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(key))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);

        }

        if(value == null) return defaultValue;

        return value;
    }

//    public long getCookieAsLong(String key, long defaultValue){
//        long value = -1;
//
//        if(req.getCookies() != null){
//            value = Arrays.stream(req.getCookies())
//                    .filter(cookie -> cookie.getName().equals(key))
//                    .map(Cookie::getValue)
//                    .mapToLong(Long::parseLong)
//                    .findFirst()
//                    .orElse(-1);
//        }
//
//        if(value == -1) return defaultValue;
//
//        return value;
//    }

    public void removeCookie(String key) {
        if(req.getCookies() != null){
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(key))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
        }
    }
}
