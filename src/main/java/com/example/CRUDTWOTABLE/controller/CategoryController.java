/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.CRUDTWOTABLE.controller;

import com.example.CRUDTWOTABLE.model.Category;
import com.example.CRUDTWOTABLE.repository.CategoryRepository;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author HieuCute
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    private List<Category> categorys = new ArrayList<>();
    @GetMapping("/list")
    public String fillAll(Model model){
        categorys = categoryRepository.findAll();
        model.addAttribute("categorys", categorys);
        return "/Category/list.html";
    }
    @GetMapping("/add")
    public String add(@ModelAttribute("category") Category category){
        return "/Category/add.html";
    }
    @PostMapping("/create")
    public String create(@Valid Category category,BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            List<FieldError> listError = bindingResult.getFieldErrors();
            Map<String,String> errors = new HashMap<>();
            for (FieldError fieldError : listError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("category", category);
            return "/Category/add.html";
        }
        categoryRepository.save(category);
        return "redirect:/category/list";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model,@ModelAttribute("category") Category categoryEdit){
        Category category = categoryRepository.findById(id).orElseThrow();
        model.addAttribute("category", category);
        return "/Category/edit.html";
    }
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,@Valid Category categoryEdit,BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            List<FieldError> listError = bindingResult.getFieldErrors();
            Map<String,String> errors = new HashMap<>();
            for (FieldError fieldError : listError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("category", categoryEdit);
            return "/Category/edit.html";
        }
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(categoryEdit.getName());
        categoryRepository.save(category);
        return "redirect:/category/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        Category category = categoryRepository.findById(id).orElseThrow();
        categoryRepository.delete(category);
        return "redirect:/category/list";
    }
}
