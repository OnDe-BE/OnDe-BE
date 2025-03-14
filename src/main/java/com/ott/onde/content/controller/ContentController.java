package com.ott.onde.content.controller;


import com.ott.onde.content.service.serviceImpl.ContentSimpleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller@RequiredArgsConstructor
@Slf4j
public class ContentController {
    private final ContentSimpleServiceImpl contentSimpleServiceImpl;

    @GetMapping(value = {"/contents"})
    public String contents(){

        return "/contents";
    }
}
