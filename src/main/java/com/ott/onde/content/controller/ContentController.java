package com.ott.onde.content.controller;


import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.entity.Content;
import com.ott.onde.content.service.ContentCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller@RequiredArgsConstructor
@Slf4j
public class ContentController {
    private final ContentCRUDService contentCRUDService;

    @GetMapping(value = {"/contents"})
    public String contents(Model model){
        List<ContentRequest> ct =  this.contentCRUDService.findContentsByTodayPick();

        model.addAttribute("ct", ct);

        return "/contents";
    }
}
