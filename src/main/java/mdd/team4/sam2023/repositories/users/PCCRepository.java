package mdd.team4.sam2023.repositories.users;


import mdd.team4.sam2023.models.users.PCC;
import mdd.team4.sam2023.models.users.PCM;
import org.springframework.data.repository.CrudRepository;

public interface PCCRepository extends CrudRepository<PCC,Integer> {
    PCC findByEmail(String email);
}
