package springapplication.models;

public class User {

    private long uid;

    private String name;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public String getPassword() {
        return password;
    }

    public User(long uid, String name, String password) {
        this.uid = uid;
        this.name = name;
        this.password = password;
    }
}
