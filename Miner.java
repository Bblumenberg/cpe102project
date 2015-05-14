import processing.core.*;
import java.util.List;

public abstract class Miner extends ResourceEntity{
    public Miner(String name, List<PImage> imgs, int resourceLimit, Point position, int rate, int animationRate, int resourceCount){
        super(name, imgs, position, rate, resourceLimit, resourceCount, "miner");
        this.animationRate = animationRate;
        this.nextAnimTime = System.currentTimeMillis() + this.animationRate;
    }
    
    public int getAnimationRate(){return animationRate;}
    
    public Point nextPosition(WorldModel world, Point destPt){
        int horiz = Sign.compare(destPt.getX(), position.getX());
        Point newPt = new Point(position.getX() + horiz, position.getY());
        if(horiz == 0 || world.isOccupied(newPt)){
            int vert = Sign.compare(destPt.getY(), position.getY());
            newPt = new Point(position.getX(), position.getY() + vert);
            if(vert == 0 || world.isOccupied(newPt)){
                newPt = new Point(position.getX(), position.getY());
            }
        }
        return newPt;
    }
}