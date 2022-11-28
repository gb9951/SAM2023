package mdd.team4.sam2023.repositories.files;

import mdd.team4.sam2023.models.files.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
