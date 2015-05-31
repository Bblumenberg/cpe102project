import java.util.List;
import processing.core.*;
import java.util.List;

public class OreBlob extends ActionedEntity{
    private AStarPather pather;
    private OccGrid<Integer> searchOverlay;
    public OreBlob(String name, List<PImage> imgs, Point position, int rate, int animationRate){
        super(name, imgs, position, rate, "blob");
        this.animationRate = animationRate;
        this.nextAnimTime = System.currentTimeMillis() + this.animationRate;
        searchOverlay = new OccGrid<Integer>(ProcessWorld.WORLD_WIDTH, ProcessWorld.WORLD_HEIGHT, 0);
    }
    
    public int getAnimationRate(){return animationRate;}
    
    public OccGrid<Integer> getOverlay(){return searchOverlay;}
    
/*    public Point blobNextPosition(WorldModel world, Point destPt){
        int horiz = Sign.compare(destPt.getX(), position.getX());
        Point newPt = new Point(position.getX() + horiz, position.getY());
        if(horiz == 0 || (world.isOccupied(newPt) && !(world.getTileOccupant(newPt) instanceof Ore))){
            int vert = Sign.compare(destPt.getY(), position.getY());
            newPt = new Point(position.getX(), position.getY() + vert);
            if(vert == 0 || (world.isOccupied(newPt) && !(world.getTileOccupant(newPt) instanceof Ore))){
                newPt = position;
            }
        }
        return newPt;
    }*/
    
    public Point blobNextPosition(WorldModel world, Point destPt){
        searchOverlay = new OccGrid<Integer>(ProcessWorld.WORLD_WIDTH, ProcessWorld.WORLD_HEIGHT, 0);
        pather = new AStarPather(this.getPosition(), destPt);
        return pather.search(world, searchOverlay, true);
    }
    
    public boolean blobToVein(WorldModel world, Vein vein){
        if(vein == null){
            return false;
        }
        Point veinPt = vein.getPosition();
        if(position.adjacent(veinPt)){
            world.removeEntity(vein);
            return true;
        }
        else{
            Point newPt = blobNextPosition(world, veinPt);
            PositionedEntity oldEntity = world.getTileOccupant(newPt);
            if(Ore.class.isInstance(oldEntity)){
                world.removeEntity(oldEntity);
                Ore oldOre = (Ore) oldEntity;
                if(oldOre.isMagic()){
                    transform(world);
                }
            }
            world.moveEntity(this, newPt);
            return false;
        }
    }
    
    public void transform(WorldModel world){
        OreBlob newBlob = new MagicBlob(getName(), ProcessWorld.magicBlobImgs, getPosition(), getRate(), getAnimationRate());
        world.removeEntity(this);
        world.addEntity(newBlob);
        newBlob.createNextAction(world);
    }
    
    public void createNextAction(WorldModel world){
        if(world.withinBounds(this.getPosition())){
            ScheduledAction myAction = new ScheduledAction(this, world, rate, new Action<OreBlob>(){
                public void method(OreBlob e, WorldModel world){
                    Vein vein = (Vein) world.findNearest(e.getPosition(), Vein.class);
                    Point veinPt;
                    if(vein != null){veinPt = vein.getPosition();}
                    else{veinPt = new Point(-1, -1);}
                    boolean found = e.blobToVein(world, vein);
                    if(found){
                        Quake quake = new Quake("quake", ProcessWorld.quakeImgs, veinPt, 100);
                        world.addEntity(quake);
                    }
                    e.createNextAction(world);
                }
            });
            Actions.addAction(myAction);
        }
    }
}