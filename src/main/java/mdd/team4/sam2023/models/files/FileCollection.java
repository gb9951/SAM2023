package mdd.team4.sam2023.models.files;

import mdd.team4.sam2023.services.FileStorageService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class FileCollection {
    FileStorageService fileStorageService;

    public FileCollection(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public File saveFile(String filename, MultipartFile file) throws IOException {
        return fileStorageService.store(filename, file);
    }

}
