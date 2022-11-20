package Paper;

public class Paper {
    private int paperId;
    private String title;
    private PaperFormat format;
    private int version;
    private boolean isReviewed;
    private String author;

    public Paper(int paperId, String title, PaperFormat format, int version, String author) {
        this.paperId = paperId;
        this.title = title;
        this.format = format;
        this.version = version;
        this.author = author;
        this.isReviewed = false;
    }
}
