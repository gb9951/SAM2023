package Paper;

import java.util.ArrayList;
import java.util.List;

public class PaperStorage {
    private static PaperStorage instance = null;
    private List<Paper> papers;

    private PaperStorage() {
        this.papers = new ArrayList<>();
    }

    public PaperStorage getInstance() {
        if(instance == null) {
            instance = new PaperStorage();
        }
        return instance;
    }

    public void addPaper(Paper paper) {

    }

    public Paper getPaper(int paperId) {
        return null;
    }
}
