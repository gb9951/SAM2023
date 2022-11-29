package mdd.team4.sam2023.startup_data;

import mdd.team4.sam2023.models.papers.Paper;
import mdd.team4.sam2023.models.reviews.Review;
import mdd.team4.sam2023.models.users.Admin;
import mdd.team4.sam2023.models.users.Author;
import mdd.team4.sam2023.models.users.PCC;
import mdd.team4.sam2023.models.users.PCM;
import mdd.team4.sam2023.repositories.papers.PaperRepository;
import mdd.team4.sam2023.repositories.reviews.ReviewRepository;
import mdd.team4.sam2023.repositories.users.AdminRepository;
import mdd.team4.sam2023.repositories.users.AuthorRepository;
import mdd.team4.sam2023.repositories.users.PCCRepository;
import mdd.team4.sam2023.repositories.users.PCMRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

public class DataLoader implements CommandLineRunner {
    private AuthorRepository authorRepository;
    private PaperRepository paperRepository;

    private PCMRepository pcmRepository;

    private PCCRepository pccRepository;
    private AdminRepository adminRepository;
    private ReviewRepository reviewRepository;

    public DataLoader(AuthorRepository authorRepository, PaperRepository paperRepository, PCMRepository pcmRepository, PCCRepository pccRepository, AdminRepository adminRepository, ReviewRepository reviewRepository) {
        this.authorRepository = authorRepository;
        this.paperRepository = paperRepository;
        this.pcmRepository = pcmRepository;
        this.pccRepository = pccRepository;
        this.adminRepository = adminRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start loading data");
        Author bob = new Author("Bob", "bob@example.com");
        authorRepository.save(bob);
        Author alice = new Author("Alice", "alice@example.com");
        authorRepository.save(alice);

        Paper bobPaper = new Paper("Bob's Paper");
        paperRepository.save(bobPaper);
        Paper alicePaper = new Paper("Alice's Paper");
        paperRepository.save(alicePaper);

        bob.getSubmittedPapers().add(bobPaper);
        bobPaper.getAuthors().add(bob);
        authorRepository.save(bob);
        paperRepository.save(bobPaper);

        alice.getSubmittedPapers().add(alicePaper);
        alicePaper.getAuthors().add(alice);
        authorRepository.save(alice);
        paperRepository.save(alicePaper);


        PCM pcm1 = new PCM("PCM1", "pcm1@example.com");
        PCM pcm2 = new PCM("PCM2", "pcm2@example.com");

        pcm1.getSelectedPapers().add(bobPaper);
        pcm2.getSelectedPapers().add(alicePaper);
        pcm2.getSelectedPapers().add(bobPaper);

        bobPaper.getSelectedBy().add(pcm1);
        bobPaper.getSelectedBy().add(pcm2);
        alicePaper.getSelectedBy().add(pcm1);

        pcm1.getAssignedPapers().add(bobPaper);
        pcm2.getAssignedPapers().add(bobPaper);
        pcm2.getAssignedPapers().add(alicePaper);

        bobPaper.getAssignedTo().add(pcm1);
        bobPaper.getAssignedTo().add(pcm2);
        alicePaper.getAssignedTo().add(pcm2);

        pcmRepository.save(pcm1);
        pcmRepository.save(pcm2);
        paperRepository.save(bobPaper);
        paperRepository.save(alicePaper);

        PCC pcc = new PCC("PCC1", "pcc1@example.com");
        pccRepository.save(pcc);

        Admin admin = new Admin("admin", "admin@example.com");
        adminRepository.save(admin);

        Review reviewForBobsPaper = new Review("It's awful. Bob must be ashamed of himself", pcm1,bobPaper);
        Review reviewForAlicePaper = new Review("meh",pcm2,alicePaper);
        Review reviewForAlicesPaperByPCM1 = new Review("garbage", pcm1, alicePaper);
        reviewRepository.save(reviewForBobsPaper);
        reviewRepository.save(reviewForAlicePaper);
        reviewRepository.save(reviewForAlicesPaperByPCM1);

    }
}
