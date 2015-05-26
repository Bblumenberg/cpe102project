import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

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
    
    public List<Point> openAdjacencyList(WorldModel world){
        List<Point> possible = new EasyList<Point>(new Point(x+1,y), new Point(x, y+1), new Point(x-1, y), new Point(x, y-1));
        List<Point> list = new ArrayList(0);
        for(Point pt : possible){
            if(world.openTile(pt)){list.add(pt);}
        }
        return list;
    }
    
    public int distanceSq(Point that){
        int xdiff = this.getX() - that.getX();
        int ydiff = this.getY() - that.getY();
        return (xdiff*xdiff) + (ydiff*ydiff);
    }
    
    public int manhatten(Point that){
        int xdiff = this.getX() - that.getX();
        int ydiff = this.getY() - that.getY();
        return Math.abs(xdiff + ydiff);
    }
    
    public boolean equals(Object tha){
        if(tha instanceof Point){
            Point that = (Point) tha;
            return (x == that.getX()) && (y == that.getY());
        }
        else{return false;}
    }
}