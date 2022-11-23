package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.repositories.reviews.ReviewRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("")
    public String getAllReviews(Model model){
        model.addAttribute("reviews",reviewRepository.findAll());
        return "reviews/list";
    }
}
