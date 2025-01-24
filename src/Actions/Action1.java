package Actions;

public class Action1 implements MyObserver{
    @Override
    public void update(String msg) {
        System.out.println("Action 1: " + msg);
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
