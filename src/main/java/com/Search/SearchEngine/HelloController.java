package com.Search.SearchEngine;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController  {

    @RequestMapping("/hello")
    public String getDocuments(){
        return "HI";
    }
}