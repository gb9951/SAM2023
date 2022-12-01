package mdd.team4.sam2023.models.reviews;

import mdd.team4.sam2023.models.files.File;
import mdd.team4.sam2023.models.files.FileCollection;
import mdd.team4.sam2023.models.templates.ReviewTemplate;

import mdd.team4.sam2023.models.templates.TemplateCollection;
import mdd.team4.sam2023.repositories.reviews.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class ReviewCollection {

    private final TemplateCollection templateCollection;

    private final ReviewRepository reviewRepository;

    private final FileCollection fileCollection;

    public ReviewCollection(TemplateCollection templateCollection, ReviewRepository reviewRepository, FileCollection fileCollection) {
        this.templateCollection = templateCollection;
        this.reviewRepository = reviewRepository;
        this.fileCollection = fileCollection;
    }

    public ReviewTemplate getReviewTemplate(){
        return templateCollection.getLatestActiveReviewTemplate();
    }

    public List<Review> findAllByPcmId(int pcmId){
        return reviewRepository.findAllByPcmId(pcmId);
    }

    public Review getReviewForPaperId(int paperId){
        return reviewRepository.findDistinctFirstByPaperId(paperId);
    }

    public void deleteReviewById(String reviewId){
        deleteReviewById(Integer.parseInt(reviewId));
    }

    public void deleteReviewById(int reviewId){
        reviewRepository.deleteById(reviewId);
    }

    public Review findReviewById(String reviewId){
        return findReviewById(Integer.parseInt(reviewId));
    }

    public Review findReviewById(int reviewId){
        return reviewRepository.findById(reviewId).get();
    }

    public void saveReview(Review review){
        reviewRepository.save(review);
    }

    public File saveReviewFile(Review review, MultipartFile file) throws IOException {
        String filename = review.getReviewFileName();
        return fileCollection.saveFile(filename, file);
    }
}
