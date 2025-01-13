package Models;

public interface InputHandler {

    String getString(String message);

    int getInt(String message);

    double getDouble(String message);

    void close();
}
