package mdd.team4.sam2023.models.templates;

import mdd.team4.sam2023.models.files.File;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ReviewTemplate extends Template{
    boolean active;

    @OneToOne
    File file;

    public ReviewTemplate(String name, String description, boolean active, File file) {
        super(name, description);
        this.active = active;
        this.file = file;
    }

    public ReviewTemplate() {
    }

    public ReviewTemplate(String name, String description, boolean active) {
        super(name, description);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
