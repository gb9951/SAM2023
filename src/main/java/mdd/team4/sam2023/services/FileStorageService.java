package mdd.team4.sam2023.services;

import mdd.team4.sam2023.models.files.File;
import mdd.team4.sam2023.repositories.files.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private FileRepository fileRepository;

    public FileStorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File store(String fileName, MultipartFile file) throws IOException {
        File newFile = new File(fileName, file.getContentType(), file.getBytes());
        return fileRepository.save(newFile);
    }

    public File getFile(Integer id) {
        return fileRepository.findById(id).get();
    }

    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}
