import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class OccGrid<T>{

    private int width;
    private int height;
    private ArrayList<List<T>> grid;
    public OccGrid(int width, int height, T value){
        this.width = width;
        this.height = height;
        this.grid  = new ArrayList<List<T>>(0);
        for(int y = 0; y < height; y++){
            List<T> inner = new ArrayList<T>(0);
            for(int x = 0; x < width; x++){
                inner.add(value);
            }
            grid.add(inner);
        }
    }
    
    public void setCell(Point pt, T value){
        this.grid.get(pt.getY()).set(pt.getX(), value);
    }
    
    public T getCell(Point pt){
        return this.grid.get(pt.getY()).get(pt.getX());
    }
}