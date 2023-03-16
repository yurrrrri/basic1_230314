package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rq.Rq;
import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password){
        if(username == null || username.trim().length() == 0){
            return RsData.of("F-3", "username을 입력해주세요.");
        }

        if(password == null || password.trim().length() == 0){
            return RsData.of("F-4", "password를 입력해주세요.");
        }

        RsData rsData = memberService.login(username, password);

        if(rsData.isSuccess()){
            rq.setCookie("username", username);
            rq.setSession("username", username);
        }
        return rsData;
    }

    @GetMapping("/member/logined")
    @ResponseBody
    public String showLogin(){
        if(rq.isLogined()){
            return "<h1>로그인 중입니다.</h1>";
        }
        return """
                <h1>로그인</h1>
                <form action="login">
                <input type="text" placeholder="아이디" name="username">
                <input type="password" placeholder="비밀번호" name="password">
                <input type="submit" value="로그인">
                </form>
                """;
    }



    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout(){
        boolean logined = rq.removeSession("username");
        if(logined){
            return RsData.of("S-1", "로그아웃 되었습니다.");
        }
        return RsData.of("S-2", "이미 로그아웃 상태입니다.");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe(){
        String username = rq.getSession("username", null);

        if(username == null){
            return RsData.of("F-1", "로그인 후 이용해주세요.");
        }

        return RsData.of("S-1", "당신의 username(은)는 %s입니다.".formatted(username));
    }
}
