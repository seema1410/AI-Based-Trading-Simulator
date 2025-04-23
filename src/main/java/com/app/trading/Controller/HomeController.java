package com.app.trading.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "welcome to my trading application";
    }
    @GetMapping("/api")
    public String secure() {
        return "secure to my trading application";
    }
}
