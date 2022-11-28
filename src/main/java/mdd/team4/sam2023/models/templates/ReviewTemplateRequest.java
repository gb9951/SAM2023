package mdd.team4.sam2023.models.templates;

import org.springframework.web.multipart.MultipartFile;

public class ReviewTemplateRequest extends ReviewTemplate{
    MultipartFile uploadedFile;

    public ReviewTemplateRequest() {
    }

    public MultipartFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(MultipartFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}
