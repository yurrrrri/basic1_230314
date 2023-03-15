package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.Rq.Rq;
import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password, HttpServletResponse resp){
        Rq rq = new Rq(resp);

        if(username == null || username.trim().length() == 0){
            return RsData.of("F-3", "username을 입력해주세요.");
        }

        if(password == null || password.trim().length() == 0){
            return RsData.of("F-4", "password를 입력해주세요.");
        }

        RsData rsData = memberService.login(username, password);

        if(rsData.isSuccess()){
            rq.setCookie("username", username);
        }
        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout(HttpServletRequest req, HttpServletResponse resp){
        Rq rq = new Rq(req, resp);
        rq.removeCookie("username");

        return RsData.of("S-1", "로그아웃 되었습니다.");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe(HttpServletRequest req){
        Rq rq = new Rq(req);
        String username = rq.getCookie("username", null);

        if(username == null){
            return RsData.of("F-1", "로그인 후 이용해주세요.");
        }

        return RsData.of("S-1", "당신의 username(은)는 %s입니다.".formatted(username));
    }
}
