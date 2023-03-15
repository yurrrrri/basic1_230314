package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password, HttpServletResponse resp){
        if(username == null || username.trim().length() == 0){
            return RsData.of("F-3", "username을 입력해주세요.");
        }

        if(password == null || password.trim().length() == 0){
            return RsData.of("F-4", "password를 입력해주세요.");
        }

        RsData rsData = memberService.login(username, password);

        if(rsData.isSuccess()){
            resp.addCookie(new Cookie("username", username));
        }
        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout(HttpServletRequest req, HttpServletResponse resp){
        if(req.getCookies() != null){
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("username"))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
        }
        return RsData.of("S-1", "로그아웃 되었습니다.");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe(HttpServletRequest req){
        String username = null;

        if(req.getCookies() != null){
            username = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("username"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if(username == null){
            return RsData.of("F-1", "로그인 후 이용해주세요.");
        }
        return RsData.of("S-1", "당신의 username(은)는 %s입니다.".formatted(username));
    }
}
