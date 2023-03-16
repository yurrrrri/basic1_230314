package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rq.Rq;
import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @PostMapping("/member/login")
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

    @GetMapping("/member/login")
    public String showLogin(){
        if(rq.isLogined()){
            return "usr/member/isLogined";
        } else return "usr/member/login";
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout(){
        boolean isLogout = rq.removeSession("username");
        if(isLogout){
            return RsData.of("S-1", "로그아웃 되었습니다.");
        }
        return RsData.of("S-2", "이미 로그아웃 상태입니다.");
    }

    @GetMapping("/member/me")
    public String showMe(Model model){
        String username = rq.getSession("username", null);
        Member member = memberService.findByUsername(username);
        model.addAttribute("member", member);
        return "usr/member/me";
    }
}
