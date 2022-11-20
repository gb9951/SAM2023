package Review;

import Paper.Paper;
import Users.User;

public class Review {
    private User reviewer;
    private Paper paper;
    private String review;
    private int rating;

    public Review(User reviewer, Paper paper, String review, int rating) {
        this.reviewer = reviewer;
        this.paper = paper;
        this.review = review;
        this.rating = rating;
    }
}
