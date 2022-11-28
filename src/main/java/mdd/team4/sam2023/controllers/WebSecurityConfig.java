package mdd.team4.sam2023.controllers;

import mdd.team4.sam2023.models.users.PCC;
import mdd.team4.sam2023.models.users.PCM;
//import mdd.team4.sam2023.models.users.User;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/", "/assignPaper").permitAll()
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
        List<mdd.team4.sam2023.models.users.User> users = new ArrayList<>();
        System.out.println(pcmRepository.findByEmail("pcm1@example.com"));
        Iterable<PCM> pcmIterable = pcmRepository.findAll();
        Iterable<PCC> pccIterable = pccRepository.findAll();
        System.out.println("PCMS: " + pcmIterable);

        users.addAll(StreamSupport.stream(pcmIterable.spliterator(), false)
                .collect(Collectors.toList()));
        users.addAll(StreamSupport.stream(pccIterable.spliterator(), false)
                .collect(Collectors.toList()));
        System.out.println("Users: " + users);

        users.forEach(pcm -> {
            UserDetails user =
                    User.withDefaultPasswordEncoder()
                            .username(pcm.getEmail())
                            .password("password")
                            .roles("PCM")
                            .build();
            userDetails.add(user);
        });
        return new InMemoryUserDetailsManager(userDetails);
    }
}