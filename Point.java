import java.lang.Math;

public class Point{

    private int x;
    private int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public boolean adjacent(Point that){
        return ((this.getX() == that.getX() && Math.abs(this.getY() - that.getY()) == 1) || (this.getY() == that.getY() && Math.abs(this.getX() - that.getX()) == 1));
    }
    
    public int distanceSq(Point that){
        int xdiff = this.getX() - that.getX();
        int ydiff = this.getY() - that.getY();
        return (xdiff*xdiff) + (ydiff*ydiff);
    }
}