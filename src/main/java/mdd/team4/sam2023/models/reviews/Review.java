package mdd.team4.sam2023.models.reviews;


import mdd.team4.sam2023.models.BaseEntity;
import mdd.team4.sam2023.models.files.File;
import mdd.team4.sam2023.models.papers.Paper;
import mdd.team4.sam2023.models.users.PCM;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Review extends BaseEntity {


    String text;

    @OneToOne
    PCM pcm;

    @OneToOne
    Paper paper;

    @OneToOne
    File file;

    public Review(String text, PCM pcm, Paper paper) {
        this.text = text;
        this.pcm = pcm;
        this.paper = paper;
    }

    public Review(String text, PCM pcm, Paper paper, File file) {
        this.text = text;
        this.pcm = pcm;
        this.paper = paper;
        this.file = file;
    }

    public Review(String text) {
        this.text = text;
    }

    public Review() {
    }

    public PCM getPcm() {
        return pcm;
    }

    public void setPcm(PCM pcm) {
        this.pcm = pcm;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Review{" +
                "text='" + text + '\'' +
                '}';
    }
}