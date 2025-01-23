package com.ott.onde.content.controller;


import com.ott.onde.content.entity.Content;
import com.ott.onde.content.service.ContentCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller@RequiredArgsConstructor
@Slf4j
public class ContentController {
    private final ContentCRUDService contentCRUDService;

    @GetMapping(value = {"/", "/main"})
    public String main(Model model){
        Content cl = this.contentCRUDService.findByContent("C_1002467");

        model.addAttribute("cl",cl);

        return "/main";
    }
}
