package Users;

import Templates.Template;

import java.util.ArrayList;

public class Admin extends User{
    ArrayList<Template> templates;

    public Admin(int id, String name, String email, String password, ArrayList<Template> templates) {
        super(id, name, email, password);
        this.templates = templates;
    }
}
