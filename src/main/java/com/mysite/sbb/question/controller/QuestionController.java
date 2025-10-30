package com.mysite.sbb.question.controller;

import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/question")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value="page", defaultValue = "0") int page){

        Page<Question> paging = questionService.getQuestionList(page);
        System.out.println("====== paging : " + paging );
        model.addAttribute("paging", paging);
        return "question/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        log.info("question : {}", question);
        return "question/detail";
    }

    @GetMapping("/create")
    public String createForm(QuestionDto questionDto, Model model){
        model.addAttribute("questionDto", questionDto);
        return "question/inputForm";
    }

    @PostMapping("/create")
    public String create(@Valid QuestionDto questionDto,
                         BindingResult bindingResult,
                         Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("questionDto", questionDto);
            return "question/inputForm";
        }


        questionService.create(questionDto);

        return "redirect:/question/list";
    }

}
