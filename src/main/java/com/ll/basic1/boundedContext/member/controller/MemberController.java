package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.boundedContext.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(){
        memberService = new MemberService();
    }

    @GetMapping("/member/login")
    @ResponseBody
    public Map login(String username, String password){
        return memberService.login(username, password);
    }
}
