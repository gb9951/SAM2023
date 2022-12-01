package mdd.team4.sam2023.models.templates;

import mdd.team4.sam2023.models.files.File;
import org.springframework.web.multipart.MultipartFile;

public class ReviewTemplateRequest extends ReviewTemplate{
    MultipartFile uploadedFile;

    public ReviewTemplateRequest() {
    }

    public ReviewTemplateRequest(String name, String description, boolean active) {
        super(name, description, active);
        this.uploadedFile = null;
    }

    public MultipartFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(MultipartFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}
