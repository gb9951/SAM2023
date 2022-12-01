package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.models.files.File;
import mdd.team4.sam2023.models.papers.Paper;
import mdd.team4.sam2023.models.papers.PaperCollection;
import mdd.team4.sam2023.models.reviews.Review;
import mdd.team4.sam2023.models.reviews.ReviewCollection;
import mdd.team4.sam2023.models.reviews.ReviewRequest;
import mdd.team4.sam2023.models.templates.ReviewTemplate;
import mdd.team4.sam2023.models.users.PCM;
import mdd.team4.sam2023.models.users.UserCollection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("{pcmId}/reviews")
public class ReviewController {
    private final ReviewCollection reviewCollection;
    private final UserCollection userCollection;
    private final PaperCollection paperCollection;

    public ReviewController(ReviewCollection reviewCollection, UserCollection userCollection, PaperCollection paperCollection) {
        this.reviewCollection = reviewCollection;
        this.userCollection = userCollection;
        this.paperCollection = paperCollection;
    }

    @GetMapping("{paperId}/new")
    public String getCreateReviewForm(Model model, @PathVariable String paperId, @PathVariable String pcmId) {
        ReviewTemplate template = reviewCollection.getReviewTemplate();
        PCM pcm = userCollection.findPCMByID(pcmId);
        Paper paper = paperCollection.findPaperByID(paperId);

        Review review = new Review();
        ReviewRequest data = new ReviewRequest();
        File file = new File();
        review.setPcm(pcm);
        review.setPaper(paper);
        review.setFile(file);
        model.addAttribute(review);
        model.addAttribute("data", data);
        model.addAttribute("template", template);
        return "reviews/new";
    }

    @PostMapping("{paperId}/new")
    public String createReview(ReviewRequest reviewRequest, @PathVariable String paperId, @PathVariable String pcmId) {
        PCM pcm = userCollection.findPCMByID(pcmId);
        Paper paper = paperCollection.findPaperByID(paperId);
        Review review = new Review(reviewRequest.getText(), pcm, paper);
        File savedFile;
        try {
            savedFile = reviewCollection.saveReviewFile(review, reviewRequest.getUploadedFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        review.setFile(savedFile);
        reviewCollection.saveReview(review);
        return "redirect:/" + pcmId + "/reviews/assigned_papers";
    }

    @GetMapping("/assigned_papers")
    public String getAssignedPapers(Model model, @PathVariable String pcmId) {
        PCM pcm = userCollection.findPCMByID(pcmId);
        List<Review> reviews = reviewCollection.findAllByPcmId(pcm.getId());
        Set<Paper> assignedPapers = pcm.getAssignedPapers();
        HashMap<Integer, Review> paperReviewMap = new HashMap<>();
        for (Review review : reviews) {
            Review reviewForPaper = reviewCollection.getReviewForPaperId(review.getReviewPaperId());
            paperReviewMap.put(review.getReviewPaperId(), reviewForPaper);
        }
        model.addAttribute("reviews", reviews);
        model.addAttribute("assigned", assignedPapers.toArray());
        model.addAttribute("pcm", pcm);
        model.addAttribute("map", paperReviewMap);
        return "reviews/assigned_papers";
    }

    @GetMapping("{reviewId}/delete")
    public String deleteReview(@PathVariable String pcmId, @PathVariable String reviewId) {
        reviewCollection.deleteReviewById(reviewId);
        return "redirect:/" + pcmId + "/reviews/assigned_papers";
    }


    @GetMapping("{reviewId}/edit")
    public String editReviewForm(@PathVariable String pcmId, @PathVariable String reviewId, Model model) {
        Review review = reviewCollection.findReviewById(reviewId);
        ReviewTemplate template = reviewCollection.getReviewTemplate();
        model.addAttribute("review", review);
        ReviewRequest data = new ReviewRequest();
        data.setText(review.getText());
        model.addAttribute("data", data);
        model.addAttribute("template", template);
        return "reviews/edit";
    }

    @PostMapping("{reviewId}/edit")
    public String editReview(ReviewRequest reviewRequest, @PathVariable String pcmId, @PathVariable String reviewId) {
        Review review = reviewCollection.findReviewById(reviewId);
        review.setText(reviewRequest.getText());
        if (reviewRequest.getUploadedFile() != null) {
            File savedFile;
            try {
                savedFile = reviewCollection.saveReviewFile(review, reviewRequest.getUploadedFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            review.setFile(savedFile);
        }
        reviewCollection.saveReview(review);
        return "redirect:/" + review.getPcm().getId() + "/reviews/assigned_papers";
    }

    @GetMapping("{reviewId}")
    public String getReview(@PathVariable String pcmId, @PathVariable String reviewId, Model model) {
        Review review = reviewCollection.findReviewById(reviewId);
        model.addAttribute("review", review);
        return "reviews/detail";
    }


}
