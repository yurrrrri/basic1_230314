package com.ll.basic1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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

    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePerson(@RequestParam int id){
        boolean removed = people.removeIf(person -> person.getId() == id);
        if(!removed){
            return id+"번 사람이 존재하지 않습니다.";
        }
        return id+"번 사람이 삭제되었습니다.";
    }
}

@AllArgsConstructor
@Getter
class Person {
    private int id;
    private String name;
    private int age;
}
