import java.util.List;
import java.util.ArrayList;

public class EasyList<T> extends ArrayList{
    
    public EasyList(T ... args){
        super(0);
        for(T arg : args){
            add(arg);
        }
    }
}