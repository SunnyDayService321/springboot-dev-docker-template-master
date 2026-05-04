package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.models.InquiryForm;
import com.example.demo.repositries.InquiryRepository;

@Controller
public class RootController {

    @Autowired
    InquiryRepository repository;

    // ホーム画面: https://127.0.0.1/
    @GetMapping("/")
    public String index() {
        return "root/index";
    }

    // お問い合わせフォーム: https://127.0.0.1/form
    @GetMapping("/form")
    public String form(InquiryForm inquiryForm) {
        return "root/form";
    }

    @PostMapping("/form")
    public String formProcess(@Validated InquiryForm inquiryForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "root/form";
        }
        repository.saveAndFlush(inquiryForm);
        inquiryForm.clear();
        model.addAttribute("message", "お問い合わせを受け付けました。");
        return "root/form";
    }
}