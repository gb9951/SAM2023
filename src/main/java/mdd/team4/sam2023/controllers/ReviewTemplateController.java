package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.models.files.File;
import mdd.team4.sam2023.models.templates.ReviewTemplate;
import mdd.team4.sam2023.models.templates.ReviewTemplateRequest;
import mdd.team4.sam2023.repositories.templates.ReviewTemplateRepository;
import mdd.team4.sam2023.services.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("templates/reviews")
public class ReviewTemplateController {

    ReviewTemplateRepository templateRepository;
    private FileStorageService fileStorageService;

    public ReviewTemplateController(ReviewTemplateRepository templateRepository, FileStorageService fileStorageService) {
        this.templateRepository = templateRepository;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("")
    public String getAllReviewTemplates(Model model){
        List<ReviewTemplate> templates = templateRepository.findAll();
        model.addAttribute(templates);
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
        String filename = (new Date()).toString();
        File file;
        try {
            file = fileStorageService.store(filename,request.getUploadedFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        template.setFile(file);
        templateRepository.save(template);
        return "redirect:";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        File file = fileStorageService.getFile(id);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(file.getType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + file.getName());
        ResponseEntity<byte[]> response = new ResponseEntity<>(file.getData(),header, HttpStatus.OK);
        return response;
    }

}
