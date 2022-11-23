package mdd.team4.sam2023.models.users;

import mdd.team4.sam2023.models.papers.Paper;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Author extends User{

    @ManyToMany
    @JoinTable(name="Author_Paper")
    Set<Paper> submittedPapers = new HashSet<>();


    public Author() {
    }

    public Author(String name, String email) {
        super(name, email);
    }

    public Set<Paper> getSubmittedPapers() {
        return submittedPapers;
    }

    public void setSubmittedPapers(Set<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }
}
