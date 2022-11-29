package mdd.team4.sam2023.models.users;


import mdd.team4.sam2023.models.papers.Paper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PCC extends User {

    public PCC() {
    }

    public PCC(String name, String email) {
        super(name, email);
    }
}