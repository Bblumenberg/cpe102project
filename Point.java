import java.lang.Math;

public class Point{

    private double x;
    private double y;
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public double get_x(){
        return this.x;
    }
    
    public double get_y(){
        return this.y;
    }
    
    public boolean adjacent(Point that){
        return ((this.get_x() == that.get_x() && Math.abs(this.get_y() - that.get_y()) == 1) || (this.get_y() == that.get_y() && Math.abs(this.get_x() - that.get_x()) == 1));
    }
    
    public double distance_sq(Point that){
        double xdiff = this.get_x() - that.get_x();
        double ydiff = this.get_y() - that.get_y();
        return (xdiff*xdiff) + (ydiff*ydiff);
    }
}