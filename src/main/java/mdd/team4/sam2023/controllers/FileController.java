package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.models.files.File;
import mdd.team4.sam2023.services.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FileController {
    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        File file = fileStorageService.getFile(id);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(file.getType()));
        String filename = file.getName();
        switch (file.getType()){
            case "application/pdf":
                filename = filename+".pdf";
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                filename = filename+".docx";
                break;
            default:
        }
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + filename);

        return new ResponseEntity<>(file.getData(),header, HttpStatus.OK);
    }

}
