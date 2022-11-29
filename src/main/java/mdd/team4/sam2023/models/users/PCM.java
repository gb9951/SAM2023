package mdd.team4.sam2023.models.users;


import mdd.team4.sam2023.models.papers.Paper;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PCM extends User{

    @ManyToMany
    @JoinTable(name = "PCM_SELECTED_PAPERS")
    Set<Paper> selectedPapers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "PCM_ASSIGNED_PAPERS")
    Set<Paper> assignedPapers = new HashSet<>();

    public PCM() {
    }

    public PCM(String name, String email) {
        super(name, email);
    }

    public Set<Paper> getSelectedPapers() {
        return selectedPapers;
    }

    public void setSelectedPapers(Set<Paper> selectedPapers) {
        this.selectedPapers = selectedPapers;
    }

    public Set<Paper> getAssignedPapers() {
        return assignedPapers;
    }

    public void setAssignedPapers(Set<Paper> assignedPapers) {
        this.assignedPapers = assignedPapers;
    }

}