package Users;

import Paper.Paper;

import java.util.ArrayList;
import java.util.HashMap;

public class PCC extends User{
    HashMap<PCM, ArrayList<Paper>> paperPCM;

    public PCC(int id, String name, String email, String password, HashMap<PCM, ArrayList<Paper>> paperPCM) {
        super(id, name, email, password);
        this.paperPCM = paperPCM;
    }
}
