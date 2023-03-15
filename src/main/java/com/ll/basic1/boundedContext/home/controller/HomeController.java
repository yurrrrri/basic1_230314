package com.ll.basic1.boundedContext.home.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    private int count;
    List<Person> people;
    int id;

    public HomeController() {
        count = -1;
        people = new ArrayList<>();
        id = 0;
    }

    @GetMapping("/home/main")
    @ResponseBody
    public String showMain(){
        return "안녕하세요.";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showMain2(){
        return "반갑습니다.";
    }

    @GetMapping("/home/main3")
    @ResponseBody
    public String showMain3(){
        return "즐거웠습니다.";
    }

    @GetMapping("/home/increase")
    @ResponseBody
    public String showIncrease(){
        count++;
        return Integer.toString(count);
    }

    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b){
        return a+b;
    }

    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(@RequestParam String name, @RequestParam(defaultValue = "0") int age){
        id++;
        people.add(new Person(id, name, age));
        return id+"번 사람이 추가되었습니다.";
    }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showPeople(){
        return people;
    }

    @GetMapping("/home/modifyPerson")
    @ResponseBody
    public String modifyPerson(int id, String name, int age){
        for(Person person : people){
            if(person.getId() == id){
                person.setName(name);
                person.setAge(age);
                return id+"번 사람이 수정되었습니다.";
            }
        }
        return id+"번 사람이 존재하지 않습니다.";

//        Person found = people
//                .stream()
//                .filter(p -> p.getId() == id)
//                .findFirst()
//                .orElse(null);
//        if(found==null){
//            return id+"번 사람이 존재하지 않습니다.";
//        }
//        found.setName(name);
//        found.setAge(age);
//        return id+"번 사람이 수정되었습니다.";
    }

    @GetMapping("/home/cookie/increase")
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int countInCookie = 0;

        if(req.getCookies() != null){
            countInCookie = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .map(Cookie::getValue)
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);    // or getAsInt();
        }
        int newCountInCookie = countInCookie+1;

        resp.addCookie(new Cookie("count", newCountInCookie+""));

        return newCountInCookie;
    }
}

@AllArgsConstructor
@Getter
@Setter
class Person {
    private int id;
    private String name;
    private int age;
}