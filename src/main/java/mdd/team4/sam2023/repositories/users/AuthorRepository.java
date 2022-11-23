package mdd.team4.sam2023.repositories.users;


import mdd.team4.sam2023.models.users.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author,Integer> {
}
