/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.CRUDTWOTABLE.controller;

import com.example.CRUDTWOTABLE.model.Category;
import com.example.CRUDTWOTABLE.model.Product;
import com.example.CRUDTWOTABLE.repository.CategoryRepository;
import com.example.CRUDTWOTABLE.repository.ProductRepository;
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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    private List<Product> products = new ArrayList<>();
    private List<Category> categorys = new ArrayList<>();

    @ModelAttribute("products")
    private List<Product> fillProducts() {
        return products = productRepository.findAll();
    }

    @ModelAttribute("categorys")
    private List<Category> fillCategorys() {
        return categorys = categoryRepository.findAll();
    }

    @GetMapping("/list")
    public String list() {
        return "/Product/list.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model,@ModelAttribute("product") Product productEdit) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "/Product/edit.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,@Valid Product productedit,BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> listError = bindingResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : listError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("product", productedit);
            return "/Product/edit.html";
        }
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(productedit.getName());
        product.setPrice(productedit.getPrice());
        product.setCategory(productedit.getCategory());
        productRepository.save(product);
        return "redirect:/product/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("product") Product product) {
        return "/Product/add.html";
    }

    @PostMapping("/create")
    public String create(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> listError = bindingResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : listError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("product", product);
            return "/Product/add.html";
        }
        productRepository.save(product);
        return "redirect:/product/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return "redirect:/product/list";
    }
}
