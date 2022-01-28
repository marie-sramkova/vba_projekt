package com.example.library.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSecured {

    @GetMapping("/helloSecured")
    public String hello() {
        return "Hello - tahle stránka je zabezpečena";
    }
}
