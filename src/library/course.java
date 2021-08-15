package library;

public class course {
    private String username, name;
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String course) {
        this.name = course;
    }
    public course(String course,String username){
        this.name = course;
        this.username = username;
    }
}
