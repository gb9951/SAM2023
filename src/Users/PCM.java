package Users;


import Paper.Paper;

import java.util.ArrayList;

public class PCM extends User {
    ArrayList<Paper> assignedPapers;
    ArrayList<Paper> selectedPapers;

    public PCM(int id, String name, String email, String password, ArrayList<Paper> assignedPapers, ArrayList<Paper> selectedPapers) {
        super(id, name, email, password);
        this.assignedPapers = assignedPapers;
        this.selectedPapers = selectedPapers;
    }

    public void assignPaper(Paper paper){
        assignedPapers.add(paper);
    }

    public void removeAssignedPaper(Paper paper){
        assignedPapers.remove(paper);
    }

    public void selectPaper(Paper paper){
        selectedPapers.add(paper);
    }

    public void unselectPaper(Paper paper){
        selectedPapers.remove(paper);
    }
}
