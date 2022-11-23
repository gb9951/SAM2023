package mdd.team4.sam2023.repositories.reviews;


import mdd.team4.sam2023.models.reviews.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review,Integer> {
}
