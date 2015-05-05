import java.util.List;
import java.util.ArrayList;

public class EasyList<T> extends ArrayList{
    
    public ArrayList(T ... args){
        this(0);
        for(T arg : args){
            add(arg);
        }
    }
}