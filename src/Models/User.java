package Models;

public abstract class User {
    private static int idGenerator = 0;
    protected int id;
    protected String userName;
    protected String password;

    public User(String userName, String password) {
        this.id = ++idGenerator;
        this.userName = userName;
        this.password = password;
    }

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;

        if (id > idGenerator) {
            setIdGenerator(id);
        }
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }

    public static void setIdGenerator(int idGenerator) {
        User.idGenerator = idGenerator;
    }
}
