/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.CRUDTWOTABLE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Hieu
 */
@Controller
@RequestMapping("/demo")
public class demoController {
    @GetMapping("/test")
    public  String index(){
        return "/Demo/index.html";
    }
}
