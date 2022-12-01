package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.models.users.Admin;
import mdd.team4.sam2023.models.users.Author;
import mdd.team4.sam2023.models.users.PCC;
import mdd.team4.sam2023.models.users.PCM;
//import mdd.team4.sam2023.models.users.User;
import mdd.team4.sam2023.repositories.users.AdminRepository;
import mdd.team4.sam2023.repositories.users.AuthorRepository;
import mdd.team4.sam2023.repositories.users.PCCRepository;
import mdd.team4.sam2023.repositories.users.PCMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private PCMRepository pcmRepository;
    @Autowired
    private PCCRepository pccRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetails = new ArrayList<>();
        Iterable<PCM> pcmIterable = pcmRepository.findAll();
        Iterable<PCC> pccIterable = pccRepository.findAll();
        Iterable<Admin> adminIterable = adminRepository.findAll();
        Iterable<Author> authorIterable = authorRepository.findAll();


        userDetails.addAll(createDetailsByRole(StreamSupport.stream(pcmIterable.spliterator(), false)
                .collect(Collectors.toList()), "PCM"));
        userDetails.addAll(createDetailsByRole(StreamSupport.stream(pccIterable.spliterator(), false)
                .collect(Collectors.toList()), "PCC"));
        userDetails.addAll(createDetailsByRole(StreamSupport.stream(adminIterable.spliterator(), false)
                .collect(Collectors.toList()), "Admin"));
        userDetails.addAll(createDetailsByRole(StreamSupport.stream(authorIterable.spliterator(), false)
                .collect(Collectors.toList()), "Author"));

        return new InMemoryUserDetailsManager(userDetails);
    }

    private List<UserDetails> createDetailsByRole(List<mdd.team4.sam2023.models.users.User> users, String role) {
        List<UserDetails> userDetails = new ArrayList<>();
        users.forEach(pcm -> {
            UserDetails user =
                    User.withDefaultPasswordEncoder()
                            .username(pcm.getEmail())
                            .password("password")
                            .roles(role)
                            .build();
            userDetails.add(user);
        });
        return userDetails;
    }
}