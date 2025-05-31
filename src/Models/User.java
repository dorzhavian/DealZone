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

    public User(int userID, String userName, String password) {
        this.id = userID;
        this.userName = userName;
        this.password = password;

        if (userID > idGenerator) {
            setIdGenerator(userID);
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
