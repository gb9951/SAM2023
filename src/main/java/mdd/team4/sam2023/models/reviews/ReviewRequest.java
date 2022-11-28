package mdd.team4.sam2023.models.reviews;

import org.springframework.web.multipart.MultipartFile;

public class ReviewRequest {
    String text;
    MultipartFile uploadedFile;

    public ReviewRequest(String text, MultipartFile uploadedFile) {
        this.text = text;
        this.uploadedFile = uploadedFile;
    }

    public ReviewRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MultipartFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(MultipartFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    @Override
    public String toString() {
        return "ReviewRequest{" +
                "text='" + text + '\'' +
                ", uploadedFile=" + uploadedFile.getName() +
                '}';
    }
}
