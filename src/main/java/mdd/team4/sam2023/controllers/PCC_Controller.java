package mdd.team4.sam2023.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PCC_Controller {
    @GetMapping("/assignPaper")
    public String assignPaper(Model model) {
        // Get all papers that have not been assigned
        System.out.println("HEREERE");
//        List<Paper> papers = new ArrayList<>();
//        Paper paper = new Paper();
//        paper.setTitle("Test Paper");
//        papers.add(paper);wq
//        papers.add(paper);
        List<String> words = new ArrayList<>();
        words.add("I");
        words.add("Like");
        words.add("Puppies");
        model.addAttribute("words", words);
        return "assignPaper";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
