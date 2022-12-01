package mdd.team4.sam2023.models.templates;

import mdd.team4.sam2023.models.files.File;
import mdd.team4.sam2023.models.files.FileCollection;
import mdd.team4.sam2023.repositories.templates.ReviewTemplateRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class TemplateCollection {
    ReviewTemplateRepository reviewTemplateRepository;
    FileCollection fileCollection;

    public TemplateCollection(ReviewTemplateRepository reviewTemplateRepository, FileCollection fileCollection) {
        this.reviewTemplateRepository = reviewTemplateRepository;
        this.fileCollection = fileCollection;
    }

    public ReviewTemplate getLatestActiveReviewTemplate(){
        return reviewTemplateRepository.findDistinctTopByActiveTrueOrderByIdDesc();
    }

    public List<ReviewTemplate> getAllReviewTemplates(){
        return reviewTemplateRepository.findAll();
    }

    public File saveReviewTemplate(ReviewTemplate template, MultipartFile file) throws IOException {
        String filename = template.getTemplateFileName();
        return fileCollection.saveFile(filename,file);
    }

    public void saveReviewTemplate(ReviewTemplate template){
        reviewTemplateRepository.save(template);
    }

    public ReviewTemplate findReviewTemplateById(String id){
        return findReviewTemplateById(Integer.parseInt(id));
    }

    private ReviewTemplate findReviewTemplateById(int id) {
        return reviewTemplateRepository.findById(id).get();
    }

    public void deleteReviewTemplateById(String id){
        deleteReviewTemplateById(Integer.parseInt(id));
    }

    private void deleteReviewTemplateById(int id) {
        reviewTemplateRepository.deleteById(id);
    }


}
