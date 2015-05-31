import processing.core.*;
import java.util.List;
import java.util.ArrayList;

public abstract class Miner extends ResourceEntity{
    private AStarPather pather;
    private OccGrid<Integer> searchOverlay;
    public Miner(String name, List<PImage> imgs, int resourceLimit, Point position, int rate, int animationRate, int resourceCount){
        super(name, imgs, position, rate, resourceLimit, resourceCount, "miner");
        this.animationRate = animationRate;
        this.nextAnimTime = System.currentTimeMillis() + this.animationRate;
        searchOverlay = new OccGrid<Integer>(ProcessWorld.WORLD_WIDTH, ProcessWorld.WORLD_HEIGHT, 0);
    }
    
    public int getAnimationRate(){return animationRate;}
    
    public void createNextAction(WorldModel world){;}
    
    public OccGrid<Integer> getOverlay(){return searchOverlay;}
    
/*    public Point nextPosition(WorldModel world, Point destPt){
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
    }*/
    
    public Point nextPosition(WorldModel world, Point destPt){
        searchOverlay = new OccGrid<Integer>(ProcessWorld.WORLD_WIDTH, ProcessWorld.WORLD_HEIGHT, 0);
        pather = new AStarPather(this.getPosition(), destPt);
        return pather.search(world, searchOverlay, false);
    }
    
/*    public Point nextPosition(WorldModel world, Point destPt){
        pather = new DFSPather(this.getPosition(), destPt, world);
        return pather.startSearch();
    }*/
}