package Users;

import Paper.Paper;

import java.util.ArrayList;

public class Author extends User{
    ArrayList<Paper> submittedPapers;

    public Author(int id, String name, String email, String password, ArrayList<Paper> submittedPapers) {
        super(id, name, email, password);
        this.submittedPapers = submittedPapers;
    }
}
