package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.models.files.File;
import mdd.team4.sam2023.models.templates.ReviewTemplate;
import mdd.team4.sam2023.models.templates.ReviewTemplateRequest;
import mdd.team4.sam2023.models.templates.TemplateCollection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("templates/reviews")
public class ReviewTemplateController {

    private final TemplateCollection templateCollection;

    public ReviewTemplateController(TemplateCollection templateCollection) {
        this.templateCollection = templateCollection;
    }

    @GetMapping("")
    public String getAllReviewTemplates(Model model){
        List<ReviewTemplate> templates = templateCollection.getAllReviewTemplates();
        model.addAttribute("templates", templates);
        return "reviewTemplates/list";
    }

    @GetMapping("/new")
    public String initializeReviewTemplateForm(Model model){
        ReviewTemplateRequest request = new ReviewTemplateRequest();
        model.addAttribute("request", request);
        return "reviewTemplates/new";
    }

    @PostMapping("/new")
    public String createReviewTemplate(ReviewTemplateRequest request){
        ReviewTemplate template = new ReviewTemplate();
        template.setName(request.getName());
        template.setDescription(request.getDescription());
        template.setActive(request.isActive());
        File file;
        try {
            file = templateCollection.saveReviewTemplate(template, request.getUploadedFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        template.setFile(file);
        templateCollection.saveReviewTemplate(template);
        return "redirect:";
    }

    @GetMapping("{id}")
    public String viewTemplate(Model model, @PathVariable String id){
        ReviewTemplate template = templateCollection.findReviewTemplateById(id);
        model.addAttribute("template", template);
        return "reviewTemplates/detail";
    }

    @GetMapping("{id}/delete")
    public String deleteTemplate(@PathVariable String id){
        templateCollection.deleteReviewTemplateById(id);
        return "redirect:/templates/reviews";
    }

    @GetMapping("{id}/edit")
    public String initializeEditTemplate(@PathVariable String id, Model model){
        ReviewTemplate template = templateCollection.findReviewTemplateById(id);
        ReviewTemplateRequest request = new ReviewTemplateRequest(template.getName(), template.getDescription(), template.isActive());
        model.addAttribute("template", template);
        model.addAttribute("request", request);
        return "reviewTemplates/edit";
    }

    @PostMapping("{id}/edit")
    public String editTemplate(ReviewTemplateRequest request ,@PathVariable String id){
        ReviewTemplate template = templateCollection.findReviewTemplateById(id);
        template.setName(request.getName());
        template.setActive(request.isActive());
        template.setDescription(request.getDescription());
        if(request.getUploadedFile() !=null){
            File file;
            try {
                file = templateCollection.saveReviewTemplate(template,request.getUploadedFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            template.setFile(file);
        }
        templateCollection.saveReviewTemplate(template);
        return "redirect:/templates/reviews";
    }



}
