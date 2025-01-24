package Actions;

public class Action2 implements MyObserver{
    @Override
    public void update(String msg) {
        System.out.println("Action 2: " + msg);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
