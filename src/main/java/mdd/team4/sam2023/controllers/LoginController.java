package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.models.users.User;
import mdd.team4.sam2023.repositories.users.AdminRepository;
import mdd.team4.sam2023.repositories.users.AuthorRepository;
import mdd.team4.sam2023.repositories.users.PCCRepository;
import mdd.team4.sam2023.repositories.users.PCMRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class LoginController {

    private final PCMRepository pcmRepository;
    private final PCCRepository pccRepository;
    private final AdminRepository adminRepository;
    private final AuthorRepository authorRepository;

    public LoginController(PCMRepository pcmRepository, PCCRepository pccRepository,
                           AdminRepository adminRepository, AuthorRepository authorRepository) {
        this.pcmRepository = pcmRepository;
        this.pccRepository = pccRepository;
        this.adminRepository = adminRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/")
    public String getHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashMap<String, String> linksMap = new HashMap<>();
        User user = null;
        String role = "";
        if(!authentication.getAuthorities().isEmpty()) {
            GrantedAuthority authority = (GrantedAuthority) authentication.getAuthorities().toArray()[0];
            role = authority.getAuthority();
        }
        switch (role) {
            case "ROLE_PCM":
                user = pcmRepository.findByEmail(authentication.getName());
                linksMap.put(String.format("/%d/reviews/assigned_papers", user.getId()), "Assigned Papers");
                break;
            case "ROLE_PCC":
                user = pccRepository.findByEmail(authentication.getName());
                linksMap.put("/assignPaper", "Assign Papers");
                break;
            case "ROLE_Author":
                user = authorRepository.findByEmail(authentication.getName());
                break;
            case "ROLE_Admin":
                user = adminRepository.findByEmail(authentication.getName());
                linksMap.put("/templates/reviews", "View Review Templates");
                break;
        }
        if(user != null) {
            model.addAttribute("name", user.getName());
        }
        if(!role.equals("")) {
            model.addAttribute("role", role.split("_")[1]);
        }
        model.addAttribute("roleLinks", linksMap);
        System.out.println(authentication.getName());
        model.addAttribute("isLoggedIn", !authentication.getName().equals("anonymousUser"));
        return "index";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String username, @RequestParam String password, Model model) {
        return "";
    }

    @PostMapping("/logout")
    public String postLogout(Model model) {
        return "";
    }
}
