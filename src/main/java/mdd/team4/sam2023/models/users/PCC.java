package mdd.team4.sam2023.models.users;


import javax.persistence.Entity;

@Entity
public class PCC extends User {

    public PCC() {
    }

    public PCC(String name, String email) {
        super(name, email);
    }
}