package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.models.files.File;
import mdd.team4.sam2023.models.papers.Paper;
import mdd.team4.sam2023.models.reviews.Review;
import mdd.team4.sam2023.models.reviews.ReviewRequest;
import mdd.team4.sam2023.models.templates.ReviewTemplate;
import mdd.team4.sam2023.models.users.PCM;
import mdd.team4.sam2023.repositories.papers.PaperRepository;
import mdd.team4.sam2023.repositories.reviews.ReviewRepository;
import mdd.team4.sam2023.repositories.templates.ReviewTemplateRepository;
import mdd.team4.sam2023.repositories.users.PCMRepository;
import mdd.team4.sam2023.services.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("{pcmId}/reviews")
public class ReviewController {
    private ReviewRepository reviewRepository;
    private PCMRepository pcmRepository;

    private PaperRepository paperRepository;
    private FileStorageService fileStorageService;

    private ReviewTemplateRepository reviewTemplateRepository;


    public ReviewController(ReviewRepository reviewRepository, PCMRepository pcmRepository, PaperRepository paperRepository, FileStorageService fileStorageService, ReviewTemplateRepository reviewTemplateRepository) {
        this.reviewRepository = reviewRepository;
        this.pcmRepository = pcmRepository;
        this.paperRepository = paperRepository;
        this.fileStorageService = fileStorageService;
        this.reviewTemplateRepository = reviewTemplateRepository;
    }

    @GetMapping("")
    public String getAllReviews(Model model){
        model.addAttribute("reviews",reviewRepository.findAll());
        return "reviews/list";
    }

    @GetMapping("{paperId}/new")
    public String getCreateReviewForm(Model model, @PathVariable String paperId, @PathVariable String pcmId){
        ReviewTemplate template = reviewTemplateRepository.findDistinctTopByActiveTrueOrderByIdDesc();
        Optional<PCM> dbPcm = pcmRepository.findById(Integer.valueOf(pcmId));
        PCM pcm;
        if(dbPcm.isPresent()){
            pcm = dbPcm.get();
        } else {
            return "reviews/error";
        }
        Optional<Paper> dbPaper = paperRepository.findById(Integer.valueOf(paperId));
        Paper paper;
        if(dbPaper.isPresent()){
            paper=dbPaper.get();
        } else {
            return "reviews/error";
        }

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
    public String createReview(ReviewRequest reviewRequest, @PathVariable String paperId, @PathVariable String pcmId){
        System.out.println("Hello test");
        Optional<PCM> dbPcm = pcmRepository.findById(Integer.valueOf(pcmId));
        PCM pcm;
        if(dbPcm.isPresent()){
            pcm = dbPcm.get();
        } else {
            return "reviews/error";
        }
        Optional<Paper> dbPaper = paperRepository.findById(Integer.valueOf(paperId));
        Paper paper;
        if(dbPaper.isPresent()){
            paper=dbPaper.get();
        } else {
            return "reviews/error";
        }

        System.out.println(reviewRequest);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String paperName = paper.getTitle().toLowerCase().strip().replace(" ", "_");
        String pcmName = pcm.getName().toLowerCase().strip().replace(" ","_");
        String filename = pcmName+"_"+paperName+timeStamp;
        File savedFile;
        try {
            savedFile = fileStorageService.store(filename, reviewRequest.getUploadedFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Review review = new Review(reviewRequest.getText(), pcm, paper, savedFile);
        reviewRepository.save(review);
        return "redirect:/"+pcmId+"/reviews/assigned_papers";
    }

    @GetMapping("/assigned_papers")
    public String getAssignedPapers(Model model, @PathVariable String pcmId){
        Optional<PCM> dbPcm = pcmRepository.findById(Integer.valueOf(pcmId));
        PCM pcm;
        if(dbPcm.isPresent()){
            pcm = dbPcm.get();
        } else {
            return "reviews/error";
        }
        List<Review> reviews = reviewRepository.findAllByPcmId(pcm.getId());
        Set<Paper> assignedPapers = pcm.getAssignedPapers();
        List<Integer> reviewedPaperIds = new ArrayList<>();
        for (Review review :
                reviews) {
            reviewedPaperIds.add(review.getPaper().getId());
        }
        HashMap<Integer, Review> paperReviewMap = new HashMap<>();
        for (Review review : reviews) {
            Review reviewForPaper = reviewRepository.findDistinctFirstByPaperId(review.getPaper().getId());
            paperReviewMap.put(review.getPaper().getId(), reviewForPaper);
        }
        model.addAttribute("reviews", reviews);
        model.addAttribute("assigned", assignedPapers.toArray());
        model.addAttribute("reviewedPaperIds", reviewedPaperIds);
        model.addAttribute("pcm", pcm);
        model.addAttribute("map", paperReviewMap);
        return "reviews/assigned_papers";
    }

    @GetMapping("{reviewId}/delete")
    public String deleteReview(@PathVariable String pcmId, @PathVariable String reviewId){
        Optional<Review> dbReview = reviewRepository.findById(Integer.valueOf(reviewId));
        Review review;
        if(dbReview.isPresent()){
            review = dbReview.get();
        } else {
            return "reviews/error";
        }
        reviewRepository.deleteById(Integer.valueOf(reviewId));
        return "redirect:/"+pcmId+"/reviews/assigned_papers";
    }


    @GetMapping("{reviewId}/edit")
    public String editReviewForm(@PathVariable String pcmId, @PathVariable String reviewId, Model model){
        Optional<Review> dbReview = reviewRepository.findById(Integer.valueOf(reviewId));
        ReviewTemplate template = reviewTemplateRepository.findDistinctTopByActiveTrueOrderByIdDesc();
        Review review;
        if(dbReview.isPresent()){
            review = dbReview.get();
        } else {
            return "reviews/error";
        }
        model.addAttribute("review", review);
        ReviewRequest data = new ReviewRequest();
        data.setText(review.getText());
        model.addAttribute("data", data);
        model.addAttribute("template", template);
        return "reviews/edit";
    }

    @PostMapping("{reviewId}/edit")
    public String editReview(ReviewRequest reviewRequest, @PathVariable String pcmId, @PathVariable String reviewId){
        Optional<Review> dbReview = reviewRepository.findById(Integer.valueOf(reviewId));
        Review review;
        if(dbReview.isPresent()){
            review = dbReview.get();
        } else {
            return "reviews/error";
        }
        review.setText(reviewRequest.getText());
        if(reviewRequest.getUploadedFile() != null){
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String paperName = review.getPaper().getTitle().toLowerCase().strip().replace(" ", "_");
            String pcmName = review.getPcm().getName().toLowerCase().strip().replace(" ","_");
            String filename = pcmName+"_"+paperName+timeStamp;
            File savedFile;
            try {
                savedFile = fileStorageService.store(filename, reviewRequest.getUploadedFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            review.setFile(savedFile);
        }
        reviewRepository.save(review);
        return "redirect:/"+review.getPcm().getId()+"/reviews/assigned_papers";
    }

    @GetMapping("{reviewId}")
    public String getReview(@PathVariable String pcmId, @PathVariable String reviewId, Model model){
        Optional<Review> dbReview = reviewRepository.findById(Integer.valueOf(reviewId));
        Review review;
        if(dbReview.isPresent()){
            review = dbReview.get();
        } else {
            return "reviews/error";
        }
        model.addAttribute("review",review);
        return "reviews/detail";
    }



}
