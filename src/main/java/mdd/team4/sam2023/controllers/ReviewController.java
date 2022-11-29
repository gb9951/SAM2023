package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.models.files.File;
import mdd.team4.sam2023.models.papers.Paper;
import mdd.team4.sam2023.models.reviews.Review;
import mdd.team4.sam2023.models.reviews.ReviewRequest;
import mdd.team4.sam2023.models.users.PCM;
import mdd.team4.sam2023.repositories.papers.PaperRepository;
import mdd.team4.sam2023.repositories.reviews.ReviewRepository;
import mdd.team4.sam2023.repositories.users.PCMRepository;
import mdd.team4.sam2023.services.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("{pcmId}/reviews")
public class ReviewController {
    private ReviewRepository reviewRepository;
    private PCMRepository pcmRepository;

    private PaperRepository paperRepository;
    private FileStorageService fileStorageService;

    public ReviewController(ReviewRepository reviewRepository, PCMRepository pcmRepository, PaperRepository paperRepository, FileStorageService fileStorageService) {
        this.reviewRepository = reviewRepository;
        this.pcmRepository = pcmRepository;
        this.paperRepository = paperRepository;
        this.fileStorageService = fileStorageService;
    }

    @ModelAttribute("pcm")
    public PCM findPCM(@PathVariable("pcmId") int pcmId) {
        System.out.println(pcmId);
        Optional<PCM> dbPcm = pcmRepository.findById(pcmId);
        if(dbPcm.isPresent()){
            PCM pcmValue = dbPcm.get();
            return pcmValue;
        } else {
            return new PCM();
        }
    }

    @InitBinder("pcm")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("")
    public String getAllReviews(Model model){
        model.addAttribute("reviews",reviewRepository.findAll());
        return "reviews/list";
    }

    @GetMapping("{paperId}/new")
    public String getCreateReviewForm(Model model, @PathVariable String paperId, @PathVariable String pcmId){
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
        return "reviews/new";
    }

    @PostMapping("{paperId}/new")
    public String createReview(ReviewRequest reviewRequest, @PathVariable String paperId, @PathVariable String pcmId){

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
        String filename = paper.getTitle().toLowerCase().strip().replace(" ", "_");
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

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        File file = fileStorageService.getFile(id);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(file.getType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + file.getName());
        ResponseEntity<byte[]> response = new ResponseEntity<>(file.getData(),header,HttpStatus.OK);
        return response;
    }



}
