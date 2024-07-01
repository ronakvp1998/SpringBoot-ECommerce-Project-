package another.world;

import org.springframework.stereotype.Component;

@Component("repo")
public class Repo {
    public void getUser(){
        System.out.println("Getting all user");
    }
}
