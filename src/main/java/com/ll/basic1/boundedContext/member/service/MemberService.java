package com.ll.basic1.boundedContext.member.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class MemberService {

    public Map login(String username, String password) {
        Map<String, String> map = new LinkedHashMap<>();

        if(username.equals("user1") && password.equals("1234")){
            map.put("resultCode", "S-1");
            map.put("msg", "user1 님 환영합니다.");
            return map;
        } else if(username.equals("user1") && !password.equals("1234")){
            map.put("resultCode", "F-1");
            map.put("msg", "비밀번호가 일치하지 않습니다.");
            return map;
        } else {
            map.put("resultCode", "F-2");
            map.put("msg", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
            return map;
        }
    }
}
