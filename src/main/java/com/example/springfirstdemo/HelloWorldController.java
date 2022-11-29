package com.example.springfirstdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Rest controller adds @Controller and @ResponseBody
// @ResponseBody results in web requests returning data rather than a view.
public class HelloWorldController {

    @GetMapping("/")
    public Object index() {
        return new Object() {
            public String name = "Nadar";
            public int age = 25;
        };
    }
}
