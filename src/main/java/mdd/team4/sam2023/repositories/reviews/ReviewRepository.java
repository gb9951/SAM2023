package mdd.team4.sam2023.repositories.reviews;


import mdd.team4.sam2023.models.reviews.Review;
import mdd.team4.sam2023.models.users.PCM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface ReviewRepository extends JpaRepository<Review,Integer> {

    List<Review> findAllByPcmId(int pcmID);

    Review findDistinctFirstByPaperId(int paperId);
}
