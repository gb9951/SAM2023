package mdd.team4.sam2023.models.papers;

import mdd.team4.sam2023.models.BaseEntity;
import mdd.team4.sam2023.models.users.Author;
import mdd.team4.sam2023.models.users.PCM;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Paper extends BaseEntity {
    String title;

    private PaperFormat format;
    private int version;
    private boolean isReviewed;

    @ManyToMany(mappedBy = "submittedPapers")
    Set<Author> authors = new HashSet<>();

    @ManyToMany(mappedBy = "selectedPapers")
    Set<PCM> selectedBy = new HashSet<>();

    @ManyToMany(mappedBy = "assignedPapers")
    Set<PCM> assignedTo = new HashSet<>();

    public Paper() {
    }

    public Paper(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<PCM> getSelectedBy() {
        return selectedBy;
    }

    public void setSelectedBy(Set<PCM> selectedBy) {
        this.selectedBy = selectedBy;
    }

    public Set<PCM> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Set<PCM> assignedTo) {
        this.assignedTo = assignedTo;
    }
}
