import java.lang.Math;

public class Point{

    private int x;
    private int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int get_x(){
        return this.x;
    }
    
    public int get_y(){
        return this.y;
    }
    
    public boolean adjacent(Point that){
        return ((this.get_x() == that.get_x() && Math.abs(this.get_y() - that.get_y()) == 1) || (this.get_y() == that.get_y() && Math.abs(this.get_x() - that.get_x()) == 1));
    }
    
    public int distance_sq(Point that){
        int xdiff = this.get_x() - that.get_x();
        int ydiff = this.get_y() - that.get_y();
        return (xdiff*xdiff) + (ydiff*ydiff);
    }
}