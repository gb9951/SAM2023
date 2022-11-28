package mdd.team4.sam2023.repositories.users;


import mdd.team4.sam2023.models.users.Admin;
import mdd.team4.sam2023.models.users.PCM;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin,Integer> {
    Admin findByEmail(String email);
}
