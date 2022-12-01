package mdd.team4.sam2023.models.papers;

import mdd.team4.sam2023.repositories.papers.PaperRepository;
import org.springframework.stereotype.Component;

@Component
public class PaperCollection {
    private PaperRepository paperRepository;

    public PaperCollection(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    public Paper findPaperByID(String id){
        return findPaperByID(Integer.parseInt(id));
    }

    public Paper findPaperByID(Integer id){
        return paperRepository.findById(id).get();
    }
}
