package mdd.team4.sam2023.repositories.papers;


import mdd.team4.sam2023.models.papers.Paper;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaperRepository extends CrudRepository<Paper,Integer> {

}
