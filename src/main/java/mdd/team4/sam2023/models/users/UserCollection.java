package mdd.team4.sam2023.models.users;

import mdd.team4.sam2023.repositories.users.PCMRepository;
import org.springframework.stereotype.Component;

@Component
public class UserCollection {
    PCMRepository pcmRepository;

    public UserCollection(PCMRepository pcmRepository) {
        this.pcmRepository = pcmRepository;
    }

    public PCM findPCMByID(String id){
        return findPCMByID(Integer.parseInt(id));
    }

    public PCM findPCMByID(Integer id){
        return pcmRepository.findById(id).get();
    }
}
