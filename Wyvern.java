import java.util.List;
import processing.core.*;
import java.util.ArrayList;

public class Wyvern extends ActionedEntity{

    private AStarPather pather;
    private OccGrid<Integer> searchOverlay;
    public Wyvern(String name, List<PImage> imgs, Point position, int rate, int animationRate){
        super(name, imgs, position, rate, "wyvern");
        this.animationRate = animationRate;
        this.nextAnimTime = System.currentTimeMillis() + this.animationRate;
        searchOverlay = new OccGrid<Integer>(ProcessWorld.WORLD_WIDTH, ProcessWorld.WORLD_HEIGHT, 0);
    }
    
    public int getAnimationRate(){return animationRate;}
    
    public OccGrid<Integer> getOverlay(){return searchOverlay;}
    
    public Point wyvernNextPosition(WorldModel world, Point destPt){
        if(this.getPosition().getX() == -1){System.out.format("(%s, %s)", this.getPosition().getX(), this.getPosition().getY());}
        searchOverlay = new OccGrid<Integer>(ProcessWorld.WORLD_WIDTH, ProcessWorld.WORLD_HEIGHT, 0);
        pather = new AStarPather(this.getPosition(), destPt, this);
        return pather.search(world, searchOverlay, true, false);
    }
    
    public boolean wyvernToBlob(WorldModel world, OreBlob blob){
        if(blob == null){return false;}
        Point blobPt = blob.getPosition();
        if(position.adjacent(blobPt)){
            blob.transform(world, blobPt);
            return true;
        }else{
            Point newPt = wyvernNextPosition(world, blobPt);
            PositionedEntity oldEntity = world.getTileOccupant(newPt);
            if(Ore.class.isInstance(oldEntity)){world.removeEntity(oldEntity);}
            world.moveEntity(this, newPt);
            return false;
        }
    }
    
    private PositionedEntity wyvernFindNearest(Point pt, WorldModel world){
        List<EntInt> oftype = new ArrayList<EntInt>(0);
        int i = 0;
        for(PositionedEntity e : world.getEntities()){
            if(OreBlob.class.isInstance(e) && !MagicBlob.class.isInstance(e)){
                oftype.add(new EntInt(e, pt.distanceSq(e.getPosition())));
            }
        }
        return world.nearestEntity(oftype);
    }
    
    public void createNextAction(WorldModel world){
        if(world.withinBounds(position)){
            ScheduledAction myAction = new ScheduledAction(this, world, rate, new Action<Wyvern>(){
                public void method(Wyvern e, WorldModel world){
                    OreBlob blob = (OreBlob) e.wyvernFindNearest(e.getPosition(), world);
                    e.wyvernToBlob(world, blob);
                    e.createNextAction(world);
                }
            });
            Actions.addAction(myAction);
        }
    }
}