package com.mysite.sbb.question.controller;

import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/question")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model){
        List<Question> questionList = questionService.getQuestionList();
        System.out.println("questionList : " + questionList );
        model.addAttribute("questionList", questionList);
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

        Question question = Question.builder()
                .content(questionDto.getContent())
                .subject(questionDto.getSubject())
                .build();
        questionService.create(question);

        return "redirect:/question/list";
    }

}
