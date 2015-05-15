import java.util.List;
import processing.core.*;

public class OreBlob extends ActionedEntity{
    public OreBlob(String name, List<PImage> imgs, Point position, int rate, int animationRate){
        super(name, imgs, position, rate, "blob");
        this.animationRate = animationRate;
        this.nextAnimTime = System.currentTimeMillis() + this.animationRate;
    }
    
    public int getAnimationRate(){return animationRate;}
    
    public Point blobNextPosition(WorldModel world, Point destPt){
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
            if(oldEntity instanceof Ore){
                world.removeEntity(oldEntity);
            }
            world.moveEntity(this, newPt);
            return false;
        }
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