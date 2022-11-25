package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.models.papers.Paper;
import mdd.team4.sam2023.models.users.PCM;
import mdd.team4.sam2023.repositories.papers.PaperRepository;
import mdd.team4.sam2023.repositories.users.PCMRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PCC_Controller {
    private final PaperRepository paperRepository;
    private final PCMRepository pcmRepository;

    public PCC_Controller(PaperRepository paperRepository, PCMRepository pcmRepository) {
        this.paperRepository = paperRepository;
        this.pcmRepository = pcmRepository;
    }

    @GetMapping("/assignPaper")
    public String getAssignPaper(Model model) {
        List<Paper> unassignedPapers = getUnassignedPapers();
        model.addAttribute("papers", unassignedPapers);
        return "assignPaper";
    }

    @PostMapping("/assignPaper")
    public String postAssignPaper(@RequestParam String ids, Model model) {
        String[] splitIds = ids.split(" ");
        int paperId = Integer.parseInt(splitIds[0]);
        int pcmId = Integer.parseInt(splitIds[1]);
        assignPaper(paperId, pcmId);
        return "assignPaper";
    }


    private void assignPaper(int paperId, int pcmId) {
        if(paperRepository.findById(paperId).isPresent()) {
            Paper paper = paperRepository.findById(paperId).get();
            if(pcmRepository.findById(pcmId).isPresent()) {
                PCM pcm = pcmRepository.findById(pcmId).get();
                pcm.getAssignedPapers().add(paper);
                paper.getAssignedTo().add(pcm);
                pcmRepository.save(pcm);
                paperRepository.save(paper);
            }
        }
    }

    private List<Paper> getUnassignedPapers() {
        List<Paper> papers = new ArrayList<>();
        for(Paper paper : paperRepository.findAll()) {
            if(paper.getAssignedTo().size() < 3) {
                papers.add(paper);
            }
        }
        return papers;
    }

}
