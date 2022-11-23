package mdd.team4.sam2023.models.users;

import javax.persistence.Entity;

@Entity
public class Admin extends User{
    public Admin() {
    }

    public Admin(String name, String email) {
        super(name, email);
    }
}
