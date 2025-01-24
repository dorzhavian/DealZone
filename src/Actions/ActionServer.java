package Actions;
import java.util.*;


public class ActionServer {
    private String msg;
    Set<MyObserver> set = new HashSet<>();

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void attach (MyObserver o)
    {
        set.add(o);
    }

    public void myNotify(){
        for (MyObserver o : set){
            o.update(msg);
        }
    }

}
